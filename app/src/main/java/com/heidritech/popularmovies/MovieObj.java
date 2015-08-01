package com.heidritech.popularmovies;

import org.json.JSONException;
import org.json.JSONObject;

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

    public MovieObj (JSONObject jsonObject) throws JSONException {
        MovieObj movie = new MovieObj();

        movie.original_title = jsonObject.getString("original_title");

    }


}
