package com.heidritech.popularmovies;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private TextView overview;
    private TextView releaseDate;
    private TextView title;
    private TextView userRating;
    private ImageView imageView;
    private String imageBaseUrl = "http://image.tmdb.org/t/p/w185/";
    MovieObj intentMovie = null;
    String baseTrailer = "http://api.themoviedb.org/3/movie/";
    String endTrailerURL = "/videos?api_key=13ebc35e0c6a99a673ac605b5e7f3710";
    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        intentMovie = (MovieObj) intent.getSerializableExtra("MovieObject");

        overview = (TextView) rootView.findViewById(R.id.movie_overview);
        releaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
        title = (TextView) rootView.findViewById(R.id.movie_title);
        userRating = (TextView) rootView.findViewById(R.id.movie_user_rating);
        imageView = (ImageView) rootView.findViewById(R.id.imageView2);

        overview.setText(intentMovie.getOverview());
        title.setText(intentMovie.getOriginal_title());
        releaseDate.setText(intentMovie.getRelease_date());
        userRating.setText(intentMovie.getVote_average());
        Picasso.with(getActivity()).load(imageBaseUrl + intentMovie.getPoster_path()).into(imageView);


        return rootView;
    }

    public void onClick()
    {
        String url = baseTrailer + intentMovie.getMovieID() + endTrailerURL;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONArray jsonArray = null;

                try {
                    jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject resultObj = jsonArray.getJSONObject(i);
                        String trailerKey = resultObj.getString("key");
                        String trailerName = resultObj.getString("name");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }





        });
    }
}
