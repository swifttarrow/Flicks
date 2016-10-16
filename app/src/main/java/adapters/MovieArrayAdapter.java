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
    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get data item for position
        Movie movie = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false); //Why's this 3rd parameter false?
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.ivImage.setImageResource(0);

            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            viewHolder.tvOverview.setText(shorten(movie.getOverview()));
            Picasso.with(getContext()).load(movie.getPosterPath()).into(viewHolder.ivImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewHolder.tvOverview.setText(movie.getOverview());
            Picasso.with(getContext()).load(movie.getBackdropPath()).into(viewHolder.ivImage);
        }


        return convertView;
    }

    private String shorten(String text){
        return text.substring(0, 75) + "...";
    }
}
