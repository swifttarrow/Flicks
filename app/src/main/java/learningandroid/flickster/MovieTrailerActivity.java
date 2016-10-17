package learningandroid.flickster;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieTrailerActivity extends YouTubeBaseActivity {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String API_KEY = "AIzaSyBcKMQ0F2VLtJNATlUS8SiVYN7oZLGujqY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        //Get all the values passed from intent.
        final String movieSrcUrl = getIntent().getExtras().getString("movieSrcUrl");
        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.youtubeFragment);
        youtubeFragment.initialize("API_KEY",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        final YouTubePlayer youTubePlayer, boolean b) {

                        client.get(movieSrcUrl, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                JSONArray json;
                                try {
                                    json = response.getJSONArray("results");

                                    //Arbitrarily picking the first trailer.
                                    String trailerKey = json.getJSONObject(0).getString("key");

                                    youTubePlayer.loadVideo(trailerKey);

                                    Log.d("DEBUG", json.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(MovieTrailerActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onStop(){
        super.onStop();
        setResult(RESULT_OK); // set result code and bundle data for response
        finish();
    }
}
