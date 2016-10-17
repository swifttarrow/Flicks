package learningandroid.flickster;

import android.app.Application;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //TODO: This is not the best way to do this:
        /*
        Nathan: you should be passing the movie object itself to the detail view
        OR
        (later) persisting objects to disk and then passing the id (edited)
        The easiest way is using serializable but better to use parcelable as we’ll discuss in the next session
        http://guides.codepath.com/android/Using-Intents-to-Create-Flows#passing-complex-data-in-a-bundle
        Never “reach into” data from another activity directly or store that data in global memory
        think of it like a website and web development. each activity stands alone.
         */
        //Get all the values passed from intent.
        String imageUrl = getIntent().getExtras().getString("imageUrl");
        String title = getIntent().getExtras().getString("title");
        String releaseDate = getIntent().getExtras().getString("releaseDate");
        float rating = Float.parseFloat(getIntent().getExtras().getString("rating"));
        String overview = getIntent().getExtras().getString("overview");

        //Get all containers for the values.
        ImageView ivImage = (ImageView) findViewById(R.id.ivMovieImage);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rbRatingBar);
        TextView tvOverview = (TextView) findViewById(R.id.tvOverview);

        //Set the values.
        loadImage(ivImage, imageUrl);
        tvTitle.setText(title);
        tvReleaseDate.setText("Release Date:" + releaseDate);
        ratingBar.setRating(rating);
        tvOverview.setText(overview);

        //TODO: Make grey filling for stars.
        //Maybe this: http://stackoverflow.com/questions/34932605/how-to-apply-ratingbar-border-without-using-an-image
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2);
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
    }

    private void loadImage(ImageView imageView, String imageURL){
        Picasso.with(this).load(imageURL).fit().centerInside()
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
