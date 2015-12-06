package com.heidritech.popularmovies;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

public class DetailActivity extends AppCompatActivity {

    public ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    private void insertMovie(MovieObj movie, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.columns.MOVIE_ID, movie.getMovieID());
        contentValues.put(MovieContract.columns.COL_TITLE, movie.getOriginal_title());
        contentValues.put(MovieContract.columns.COL_BACKDROP, movie.getBackdrop_path());
        contentValues.put(MovieContract.columns.COL_POSTER, movie.getPoster_path());
        contentValues.put(MovieContract.columns.COL_RELEASE, movie.getRelease_date());
        contentValues.put(MovieContract.columns.COL_VOTE, movie.getVote_average());

        database.insert(MovieContract.columns.TABLE_NAME, null, contentValues);
    }

    private void SavePreferences(){
        /*SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("state", imageButton.isSelected());
        editor.commit();*/   // I missed to save the data to preference here,.
    }

    private void LoadPreferences(){
       /* SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Boolean  state = sharedPreferences.getBoolean("state", false);
        imageButton.setSelected(state);*/
    }

    @Override
    public void onBackPressed() {
        SavePreferences();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
