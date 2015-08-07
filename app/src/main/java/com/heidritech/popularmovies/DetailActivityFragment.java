package com.heidritech.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        MovieObj intentMovie = (MovieObj) intent.getSerializableExtra("MovieObject");

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
}
