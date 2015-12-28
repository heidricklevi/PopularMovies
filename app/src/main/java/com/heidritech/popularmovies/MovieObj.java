package com.heidritech.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Levi James H on 7/31/2015.
 */
public class MovieObj implements Serializable {

    private String poster_path;
    private String original_title;
    private String overview;
    private String vote_average;
    private String vote_count;
    private String release_date;
    private String movieID;
    private String backdrop_path;
    private String runtime;


    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_count(){return vote_count;}

    public String getMovieID() {return movieID;}

    public String getRuntime() {
        return runtime;
    }



    public String getBackdrop_path() {return backdrop_path;}

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public MovieObj (){}


    /*
    @param jsonObject; the json object to be converted into a movieObj
    @return the new movie object
     */
    public static MovieObj deserialize(JSONObject jsonObject) throws JSONException {
        MovieObj movie = new MovieObj();
        movie.original_title = jsonObject.getString("original_title");
        movie.overview = jsonObject.getString("overview");
        movie.poster_path = jsonObject.getString("poster_path");
        movie.release_date = jsonObject.getString("release_date");
        movie.vote_average = jsonObject.getString("vote_average");
        movie.movieID = jsonObject.getString("id");
        movie.backdrop_path = jsonObject.getString("backdrop_path");
        movie.vote_count = jsonObject.getString("vote_count");
        /*movie.runtime = jsonObject.getString("runtime");*/

       /* JSONArray jsonArray = jsonObject.getJSONArray("genre_ids");*/
       /* for(int i = 0; i < jsonArray.length(); i++){

            ArrayList<String> local = new ArrayList<>();
            local = movie.genres;

            local.add(jsonArray.getString(i));
        }
*/
        return movie;

    }

    public static ArrayList<MovieObj> fromJsonArray(JSONArray jsonArray) throws JSONException
    {
        ArrayList<MovieObj> movies = new ArrayList<>(jsonArray.length());


        JSONObject jsonItems = null;

        for (int i = 0; i < jsonArray.length(); i++)
        {
            jsonItems = jsonArray.getJSONObject(i);

            MovieObj movie = MovieObj.deserialize(jsonItems);

            if (movie != null)
                movies.add(movie);
        }

        return movies;
    }


}
