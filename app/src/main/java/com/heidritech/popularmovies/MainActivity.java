package com.heidritech.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView;
        gridView = (GridView) findViewById(R.id.gridview );
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

 /*   public class FetchMovieData extends AsyncTask<Void, Void, String[]>
    {

        private final String LOG_TAG = FetchMovieData.class.getSimpleName();

        public String [] movieJsonData(String movieJsonStr) throws JSONException
        {
            String [] resultStr = {" "};
            final String MDB_RESULTS = "results";
            final String MDB_POSTER_PATH ="poster_path";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray resultsArray = movieJson.getJSONArray(MDB_RESULTS);

            for (int i = 0; i < resultsArray.length(); i++)
            {
                JSONObject movie = resultsArray.getJSONObject(i);
                resultStr[i] = movie.getString(MDB_POSTER_PATH);
            }

            for (String s : resultStr) {
                Log.v(LOG_TAG, "Movie: " + s);
            }
            return resultStr;
        }


        @Override
        protected String [] doInBackground(Void... params)
        {

            final String API_KEY = "13ebc35e0c6a99a673ac605b5e7f3710";
            final String image_BaseUrl = "http://image.tmdb.org/t/p/w185/";
*//*
            final String API_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[------]";
*//*
            final String apiBaseUrl = "http://api.themoviedb.org/3/discover/movie?";

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;
            try {
                Uri.Builder builtUri = Uri.parse(apiBaseUrl).buildUpon()
                        .appendQueryParameter("sort_by", "popularity.desc")
                        .appendQueryParameter("api_key", API_KEY);
                builtUri.build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }

                if (buffer.length() == 0)
                {
                    return null;
                }

                movieJsonStr = buffer.toString();


            }
            catch (Exception e)
            {
                Log.e("AsyncTaskDoInBackground", "Error", e);

            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("ForecastFragment", "Error closing stream", e);
                }
            }

            try {
                return movieJsonData(movieJsonStr);
            } catch (JSONException e) {

                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }
*///
    //}
}
