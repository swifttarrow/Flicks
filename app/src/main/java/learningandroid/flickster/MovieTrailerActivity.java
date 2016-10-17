package learningandroid.flickster;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieTrailerActivity extends YouTubeBaseActivity {
    private static OkHttpClient client = new OkHttpClient();
    private static String API_KEY = "AIzaSyBcKMQ0F2VLtJNATlUS8SiVYN7oZLGujqY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        //Get all the values passed from intent.
        final String movieSrcUrl = getIntent().getExtras().getString("movieSrcUrl");
        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.youtubeFragment);
        youtubeFragment.initialize(API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        final YouTubePlayer youTubePlayer, boolean b) {


                        Request request = new Request.Builder().url(movieSrcUrl).build();
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

                                            try {
                                                JSONArray json = new JSONObject(response.body().string()).getJSONArray("results");

                                                //Arbitrarily picking the first trailer.
                                                String trailerKey = json.getJSONObject(0).getString("key");

                                                youTubePlayer.loadVideo(trailerKey);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            ;
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
