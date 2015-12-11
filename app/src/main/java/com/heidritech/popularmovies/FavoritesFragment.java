package com.heidritech.popularmovies;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Levi on 12/6/2015.
 */
public class FavoritesFragment extends Fragment {

    private String[] columns = {MovieContract.columns.MOVIE_ID, MovieContract.columns.COL_BACKDROP,MovieContract.columns.COL_TITLE, MovieContract.columns.COL_POSTER, MovieContract.columns.COL_RELEASE,
            MovieContract.columns.COL_VOTE, MovieContract.columns.COL_OVERVIEW};
    private GridView gridView;
    private ImageAdapter movieAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        gridView = (GridView)view.findViewById(R.id.gridview );
        ArrayList<MovieObj> aMovies = new ArrayList<>();
        movieAdapter = new ImageAdapter(getActivity(),aMovies);
        gridView.setAdapter(movieAdapter);

        favNetworkCall("254128");


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                MovieObj iMovie = movieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("MovieObject", iMovie);
                startActivity(intent);

            }
        });

        return view;
    }

    public void favNetworkCall(String movieID)
    {
        String baseApi = "http://api.themoviedb.org/3/movie/";
        String api_K = "?api_key=13ebc35e0c6a99a673ac605b5e7f3710";
        String url = baseApi + movieID + api_K;

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                ArrayList<MovieObj> movieObjs = getMarkedFavs();

                for (MovieObj obj : movieObjs)
                    movieAdapter.add(obj);

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();

        movieAdapter.clear();
        favNetworkCall("254128");
    }

    public ArrayList<MovieObj> getMarkedFavs()
    {
        MovieDBHelper dbHelper = new MovieDBHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<MovieObj> movieObjs = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(MovieContract.columns.TABLE_NAME, columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MovieObj obj = setMovie(cursor);
            movieObjs.add(obj);
            cursor.moveToNext();
        }

        cursor.close();

        return movieObjs;

    }

    public MovieObj setMovie(Cursor cursor)
    {
        /*{MovieContract.columns.MOVIE_ID, MovieContract.columns.COL_BACKDROP, MovieContract.columns.COL_TITLE, MovieContract.columns.COL_POSTER, MovieContract.columns.COL_RELEASE,
            MovieContract.columns.COL_VOTE};*/
        MovieObj obj = new MovieObj();

        obj.setMovieID(cursor.getString(0));
        obj.setBackdrop_path(cursor.getString(1));
        obj.setOriginal_title(cursor.getString(2));
        obj.setPoster_path(cursor.getString(3));
        obj.setRelease_date(cursor.getString(4));
        obj.setVote_average(cursor.getString(5));
        obj.setOverview(cursor.getString(6));

        return obj;
    }













}
