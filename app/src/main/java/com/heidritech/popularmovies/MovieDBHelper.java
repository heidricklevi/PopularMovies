package com.heidritech.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Levi on 11/20/2015.
 */
public class MovieDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "movie.db";
    public static final int DB_VERSION = 1;
    SQLiteDatabase database;
    String CREATE_TABLE = "CREATE TABLE " + MovieContract.columns.TABLE_NAME + " (" +
            MovieContract.columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MovieContract.columns.MOVIE_ID + "INTEGER," +
            MovieContract.columns.COL_TITLE + " TEXT NOT NULL, " +
            MovieContract.columns.COL_POSTER + " TEXT NOT NULL, " +
            MovieContract.columns.COL_BACKDROP + " TEXT NOT NULL, " +
            MovieContract.columns.COL_RELEASE + " TEXT NOT NULL, " +
            MovieContract.columns.COL_VOTE + " REAL NOT NULL, " + " );";



    public MovieDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + MovieContract.columns.TABLE_NAME);
        onCreate(db);
    }


}
