package com.heidritech.popularmovies;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class gridFragment extends Fragment
{
    private final String now_playing =  "http://api.themoviedb.org/3/movie/now_playing?";
    private final String API_KEY = "13ebc35e0c6a99a673ac605b5e7f3710";
    private GridView gridView;
    private ImageAdapter movieAdapter;
    private TMDBClient client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_main, container, false);

        gridView = (GridView)view.findViewById(R.id.gridview );
        ArrayList<MovieObj> aMovies = new ArrayList<>();
        movieAdapter = new ImageAdapter(getActivity(),aMovies);
        gridView.setAdapter(movieAdapter);
        try {
            fetchMovieData();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                MovieObj iMovie = movieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("MovieObject", iMovie);
                startActivity(intent);

            }
        });



        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        movieAdapter.clear();
        try {
            fetchMovieData();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public URL getNowPlayingUrl() throws MalformedURLException {

        Uri.Builder builder = Uri.parse(now_playing).buildUpon()
                .appendQueryParameter("api_key", API_KEY);
        builder.build();

        URL url = new URL(builder.toString());

        return url;
    }

    private void fetchMovieData() throws MalformedURLException {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String string = sharedPreferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_pop));
        Boolean nowPlaying = sharedPreferences.getBoolean(getString(R.string.pref_now_playing_key), true);

        client = new TMDBClient();
        if (nowPlaying)
        {
            String url = String.valueOf(getNowPlayingUrl());
            AsyncHttpClient client = new AsyncHttpClient();

            client.get(url, new JsonHttpResponseHandler()
            {
                JSONArray jsonArray = null;

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    try {
                        jsonArray = response.getJSONArray("results");
                        ArrayList<MovieObj> movieObjs = MovieObj.fromJsonArray(jsonArray);
                        for (MovieObj movieObj : movieObjs)
                            movieAdapter.add(movieObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        else
        {
            client.getMovies(new JsonHttpResponseHandler() {
                /**
                 * Returns when request succeeds
                 *
                 * @param statusCode http response status line
                 * @param headers    response headers if any
                 * @param response   parsed response if any
                 */
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = response.getJSONArray("results");
                        ArrayList<MovieObj> movies = MovieObj.fromJsonArray(jsonArray);

                        if (string.equals(getString(R.string.pref_sort_highest_rated))) {
                            Collections.sort(movies, new Comparator<MovieObj>() {
                                @Override
                                public int compare(MovieObj lhs, MovieObj rhs) {
                                    return lhs.getVote_average().compareTo(rhs.getVote_average());
                                }
                            });
                        }

                        for (MovieObj movie : movies)
                            movieAdapter.add(movie);

                        movieAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
