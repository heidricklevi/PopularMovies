import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heidritech.popularmovies.MovieContract;
import com.heidritech.popularmovies.MovieDBHelper;
import com.heidritech.popularmovies.MovieObj;

import java.util.ArrayList;

/**
 * Created by Levi on 11/27/2015.
 */
public class MovieDB {

    SQLiteDatabase sqLiteDatabase;
    MovieDBHelper dbHelper;
    private String[] columns = {MovieContract.columns.MOVIE_ID, MovieContract.columns.COL_BACKDROP, MovieContract.columns.COL_TITLE, MovieContract.columns.COL_POSTER, MovieContract.columns.COL_RELEASE,
            MovieContract.columns.COL_VOTE};

    public void datasource(Context context) {
        dbHelper = new MovieDBHelper(context);
    }

    public void open() {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        dbHelper.close();
    }

    public void add(MovieObj movie)
    {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.columns.MOVIE_ID, movie.getMovieID());
        contentValues.put(MovieContract.columns.COL_TITLE, movie.getOriginal_title());
        contentValues.put(MovieContract.columns.COL_BACKDROP, movie.getBackdrop_path());
        contentValues.put(MovieContract.columns.COL_POSTER, movie.getPoster_path());
        contentValues.put(MovieContract.columns.COL_RELEASE, movie.getRelease_date());
        contentValues.put(MovieContract.columns.COL_VOTE, movie.getVote_average());

        sqLiteDatabase.insert(MovieContract.columns.TABLE_NAME, null, contentValues);
    }

    public ArrayList<MovieObj> getMarkedFavs()
    {
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

        return obj;
    }

}
