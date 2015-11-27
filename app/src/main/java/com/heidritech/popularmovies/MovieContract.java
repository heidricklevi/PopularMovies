package com.heidritech.popularmovies;

import android.provider.BaseColumns;

/**
 * Created by Levi on 11/21/2015.
 */
public class MovieContract {


    public static class columns implements BaseColumns {


        public static final String TABLE_NAME = "movie";
        public static final String COL_TITLE = "title";
        public static final String COL_VOTE = "rating";
        public static final String COL_POSTER = "poster_path";
        public static final String COL_RELEASE = "release_date";
        public static final String MOVIE_ID = "movie_id";
        public static final String COL_BACKDROP = "backdrop_path";
    }
}
