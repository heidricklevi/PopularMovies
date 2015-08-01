package com.heidritech.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Levi James H on 7/31/2015.
 */
public class MovieObj {

    private String poster_path;
    private String original_title;
    private String overview;
    private double vote_average;
    private String release_date;

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
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
        movie.vote_average = jsonObject.getDouble("vote_average");

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
