package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by swifttarrow on 10/13/2016.
 */
public class Movie {
    public enum Popularity{
        POPULAR, BORING
    }
    private int id;
    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;
    private String releaseDate; //TODO: Model this with a Date object.
    private float rating;

    public static final String movieImageBaseURL = "https://image.tmdb.org/t/p/w342/%s";
    public static final String movieSrcURL = "https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    public String getPosterPath() {
        return String.format(movieImageBaseURL, posterPath);
    }

    public String getBackdropPath() { return String.format(movieImageBaseURL, backdropPath); }

    public String getMovieSrcUrl() { return String.format(movieSrcURL, id); }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() { return rating; }

    public String getReleaseDate() { return releaseDate; }

    public Popularity getPopularity(){
        return rating > 5.0 ? Popularity.POPULAR : Popularity.BORING;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt("id");
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.rating = Float.parseFloat(jsonObject.getString("vote_average"));
        this.releaseDate = jsonObject.getString("release_date");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for (int i=0; i<array.length(); i++){
            try{
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
