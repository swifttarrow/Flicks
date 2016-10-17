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

import java.util.ArrayList;

import adapters.MovieArrayAdapter;
import cz.msebera.android.httpclient.Header;
import models.Movie;

public class MovieActivity extends AppCompatActivity {
    private ArrayList<Movie> movies = new ArrayList<>();
    private MovieArrayAdapter movieAdapter;
    private ListView lvItems;
    private SwipeRefreshLayout swipeContainer;
    private String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvItems = (ListView) findViewById(R.id.lvMovies);
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        lvItems.setOnItemClickListener(
                new android.widget.AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {
                        Intent i = new Intent(MovieActivity.this, MovieDetailActivity.class);
                        Movie thisMovie = movieAdapter.getItem(pos);
                        i.putExtra("imageUrl", thisMovie.getPosterPath());
                        i.putExtra("title", thisMovie.getOriginalTitle());
                        i.putExtra("releaseDate", thisMovie.getReleaseDate());
                        i.putExtra("rating", String.valueOf(thisMovie.getRating()));
                        i.putExtra("overview", thisMovie.getOverview());

                        startActivity(i);
                    }
                });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
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
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults;
                try {
                    if (isSwipeRefresh){
                        movieAdapter.clear();
                    }
                    movieJsonResults = response.getJSONArray("results");
                    movieAdapter.addAll(Movie.fromJSONArray(movieJsonResults));

                    if (isSwipeRefresh){
                        swipeContainer.setRefreshing(false);
                    }
                    Log.d("DEBUG", movieJsonResults.toString());
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
