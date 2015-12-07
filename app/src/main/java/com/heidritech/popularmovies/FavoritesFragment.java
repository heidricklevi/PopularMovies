package com.heidritech.popularmovies;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Levi on 12/6/2015.
 */
public class FavoritesFragment extends Fragment {

    private String[] columns = {MovieContract.columns.MOVIE_ID, MovieContract.columns.COL_BACKDROP,MovieContract.columns.COL_TITLE, MovieContract.columns.COL_POSTER, MovieContract.columns.COL_RELEASE,
            MovieContract.columns.COL_VOTE, MovieContract.columns.COL_OVERVIEW};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
