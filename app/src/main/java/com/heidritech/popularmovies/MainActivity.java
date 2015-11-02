package com.heidritech.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends ActionBarActivity {

    private GridView gridView;
    private ImageAdapter movieAdapter;
    private TMDBClient client;
    private int resource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridview );
        ArrayList<MovieObj> aMovies = new ArrayList<>();
        movieAdapter = new ImageAdapter(this,aMovies);
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
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class).putExtra("MovieObject", iMovie);
                startActivity(intent);
            }
        });


    }



    private void fetchMovieData() throws MalformedURLException {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String string = sharedPreferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_pop));


        client = new TMDBClient();

        client.getMovies(new JsonHttpResponseHandler()
        {
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

                    if (string.equals(getString(R.string.pref_sort_highest_rated)))
                    {
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

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();

        movieAdapter.clear();
            try {
                fetchMovieData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
