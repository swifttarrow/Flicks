package adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import learningandroid.flickster.R;
import models.Movie;

/**
 * Created by swifttarrow on 10/15/2016.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    private static class ViewHolder{
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
    }
    private static class ViewHolder2{
        ImageView ivImage;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getViewTypeCount(){
        return Movie.Popularity.values().length;
    }

    @Override
    public int getItemViewType(int position){
        return getItem(position).getPopularity().ordinal();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get data item for position
        Movie movie = getItem(position);
        Movie.Popularity popularity = Movie.Popularity.values()[getItemViewType(position)];

        ViewHolder viewHolder;
        ViewHolder2 viewHolder2;
        if (popularity == Movie.Popularity.BORING){
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = getInflatedLayoutForType(getItemViewType(position));
                viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
                viewHolder.ivImage.setImageResource(0);

                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
                convertView.setTag(viewHolder); //TODO: Understand this more.
            } else {
                viewHolder = (ViewHolder) convertView.getTag(); //TODO: Understand this more.
            }
            viewHolder.tvTitle.setText(movie.getOriginalTitle());
            int orientation = getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                viewHolder.tvOverview.setText(shorten(movie.getOverview()));
                loadImage(viewHolder.ivImage, movie.getPosterPath());
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                viewHolder.tvOverview.setText(movie.getOverview());
                loadImage(viewHolder.ivImage, movie.getBackdropPath());
            }
        } else if (popularity == Movie.Popularity.POPULAR){
            if (convertView == null){
                viewHolder2 = new ViewHolder2();
                convertView = getInflatedLayoutForType(getItemViewType(position));
                viewHolder2.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
                viewHolder2.ivImage.setImageResource(0);

                convertView.setTag(viewHolder2);
            } else {
                viewHolder2 = (ViewHolder2) convertView.getTag();
            }
            loadImage(viewHolder2.ivImage, movie.getBackdropPath());
        }

        return convertView;
    }

    private String shorten(String text){
        if (text.length() <= 75){
            return text;
        }
        return text.substring(0, 75) + "...";
    }

    private void loadImage(ImageView imageView, String imageURL){
        Picasso.with(getContext()).load(imageURL).fit().centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(imageView);
    }

    private View getInflatedLayoutForType(int type) {
        if (type == Movie.Popularity.BORING.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        } else if (type == Movie.Popularity.POPULAR.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_popular, null);
        } else {
            return null;
        }
    }
}
