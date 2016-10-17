package learningandroid.flickster;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailActivity extends AppCompatActivity {
    private static int RESULT_CODE = 20;

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
        Bundle extras = getIntent().getExtras();
        String imageUrl = extras.getString("imageUrl");
        String title = extras.getString("title");
        String releaseDate = extras.getString("releaseDate");
        float rating = Float.parseFloat(extras.getString("rating"));
        String overview = extras.getString("overview");
        final String movieSrcUrl = extras.getString("movieSrcUrl");

        //Get all containers for the values.
        ImageView ivImage = (ImageView) findViewById(R.id.ivMovieImage);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MovieDetailActivity.this, MovieTrailerActivity.class);

                i.putExtra("movieSrcUrl", movieSrcUrl);
                startActivityForResult(i, RESULT_CODE);
            }
        });

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
                .transform(new RoundedCornersTransformation(10, 10))
                .into(imageView);
    }
}
