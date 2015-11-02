package com.heidritech.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Levi James H on 8/1/2015.
 */
public class TMDBClient {
    private final String API_KEY = "13ebc35e0c6a99a673ac605b5e7f3710";
    private final String image_BaseUrl = "http://image.tmdb.org/t/p/w185/";
    private final String apiBaseUrl = "http://api.themoviedb.org/3/discover/movie?";
    /*http://api.themoviedb.org/3/movie/307081/videos?api_key=13ebc35e0c6a99a673ac605b5e7f3710*/
    private AsyncHttpClient client;
    public TMDBClient () {this.client = new AsyncHttpClient();}

    public URL buildURL() throws MalformedURLException {
        Uri.Builder builtUri = Uri.parse(apiBaseUrl).buildUpon()
                .appendQueryParameter("sort_by", "popularity.desc")
                .appendQueryParameter("api_key", API_KEY);
        builtUri.build();

        URL url = new URL(builtUri.toString());

        return url;
    }

    public void getNowPlaying(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String now_playing = sharedPreferences.getString(String.valueOf(R.string.pref_now_playing_key), String.valueOf((R.string.pref_default_nowplaying)));



    }

    public void getMovies(JsonHttpResponseHandler handler) throws MalformedURLException {
        String url = String.valueOf(buildURL());
        client.get(url,handler);
    }

}
