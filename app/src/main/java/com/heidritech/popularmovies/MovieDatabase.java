package com.heidritech.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Levi on 11/20/2015.
 */
public class MovieDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "movie.db";



    public MovieDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + MovieContract.columns.TABLE_NAME+ " ("+
                MovieContract.columns.COL_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieContract.columns.COL_TITLE + " TEXT NOT NULL, "+
                MovieContract.columns.COL_POSTER+ " TEXT NOT NULL, "+
                MovieContract.columns.COL_BACKDROP+ " TEXT NOT NULL, "+
                MovieContract.columns.COL_RELEASE+ " TEXT NOT NULL, "+
                MovieContract.columns.COL_VOTE+ " REAL NOT NULL, "+ " );");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
