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
    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;
    private double rating;

    public static final String movieImageBaseURL = "https://image.tmdb.org/t/p/w342";

    public String getPosterPath() {
        return String.format(movieImageBaseURL + "/%s", posterPath);
    }

    public String getBackdropPath() { return String.format(movieImageBaseURL + "/%s", backdropPath); }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() { return rating; }

    public Popularity getPopularity(){
        return rating > 5.0 ? Popularity.POPULAR : Popularity.BORING;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.rating = Double.valueOf(jsonObject.getString("vote_average"));
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
