package learningandroid.flickster;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import adapters.MovieArrayAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import cz.msebera.android.httpclient.Header;
import models.Movie;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {
    private ArrayList<Movie> movies = new ArrayList<>();
    private MovieArrayAdapter movieAdapter;

    @BindView(R.id.lvMovies)
    ListView lvItems;

    @OnItemClick(R.id.lvMovies)
    public void onItemClick(AdapterView<?> adapter,
                            View item, int pos, long id) {
        Movie thisMovie = movieAdapter.getItem(pos);

        if (thisMovie.getPopularity() == Movie.Popularity.BORING){
            Intent i = new Intent(MovieActivity.this, MovieDetailActivity.class);

            i.putExtra("imageUrl", thisMovie.getPosterPath());
            i.putExtra("title", thisMovie.getOriginalTitle());
            i.putExtra("releaseDate", thisMovie.getReleaseDate());
            i.putExtra("rating", String.valueOf(thisMovie.getRating()));
            i.putExtra("overview", thisMovie.getOverview());
            i.putExtra("movieSrcUrl", thisMovie.getMovieSrcUrl());

            startActivity(i);
        } else if (thisMovie.getPopularity() == Movie.Popularity.POPULAR){
            Intent i = new Intent(MovieActivity.this, MovieTrailerActivity.class);

            i.putExtra("movieSrcUrl", thisMovie.getMovieSrcUrl());
            startActivityForResult(i, RESULT_CODE);
        }
    }

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    private String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static OkHttpClient client = new OkHttpClient();
    private int RESULT_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovieData(true);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        getMovieData(false);
    }

    public void getMovieData(final boolean isSwipeRefresh){
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isSwipeRefresh){
                                movieAdapter.clear();
                            }
                            try {
                                JSONArray movieJsonResults = new JSONObject(response.body().string()).getJSONArray("results");
                                movieAdapter.addAll(Movie.fromJSONArray(movieJsonResults));
                            } catch (Exception e){
                                //TODO: What should this do?
                            }
                            if (isSwipeRefresh){
                                swipeContainer.setRefreshing(false);
                            }
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
