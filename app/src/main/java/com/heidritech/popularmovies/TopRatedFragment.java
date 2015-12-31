package com.heidritech.popularmovies;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Levi on 12/28/2015.
 */
public class TopRatedFragment extends Fragment {
    private CardAdapter movieAdapter;

    int  page = 1;

    String topRatedURL = "http://api.themoviedb.org/3/movie/top_rated";
    private final String API_KEY = "13ebc35e0c6a99a673ac605b5e7f3710";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.card_grid, container, false);
        GridView gridView = (GridView)view.findViewById(R.id.gridview );
        ArrayList<MovieObj> aMovies = new ArrayList<>();
        movieAdapter = new CardAdapter(getActivity(),aMovies);
        gridView.setAdapter(movieAdapter);
        getTopRatedMovieData(page);


        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount >= totalItemCount) {


                    getTopRatedMovieData(page);
                    System.out.println("Inner Page = " + page);
                    page += 1;
                }
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                MovieObj iMovie = movieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("MovieObject", iMovie);
                        startActivity(intent);

                    }
                });

        return view;
    }

    public String formattedURL (int pageParam)
    {
        Uri.Builder builtUri = Uri.parse(topRatedURL).buildUpon()
                                .appendQueryParameter("api_key", API_KEY)
                                .appendQueryParameter("page", String.valueOf(pageParam));


        return String.valueOf(builtUri);
    }

    /*@Override*/
    /*public void onResume() {
        super.onResume();
        movieAdapter.clear();
        getTopRatedMovieData();
    }*/

    public void getTopRatedMovieData(int page)
    {

        AsyncHttpClient client = new AsyncHttpClient();

        System.out.println("This is the URL: " + formattedURL(page));

        client.get(formattedURL(page), new JsonHttpResponseHandler()
        {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("results");
                    ArrayList<MovieObj> movies = MovieObj.fromJsonArray(jsonArray);

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


