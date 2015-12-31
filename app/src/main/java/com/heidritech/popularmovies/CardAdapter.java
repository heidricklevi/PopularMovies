package com.heidritech.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Levi on 12/29/2015.
 */
public class CardAdapter extends ArrayAdapter<MovieObj> {

    private String imageBaseUrl = "http://image.tmdb.org/t/p/w185/";

    public CardAdapter (Context context, ArrayList<MovieObj> aMovie)
    {
        super(context, 0, aMovie);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        MovieObj movie = getItem(position);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.top_rated_cardview, parent, false);
        }

        imageView = (ImageView) convertView.findViewById(R.id.grid_image);

        Picasso.with(getContext()).load(imageBaseUrl + movie.getPoster_path()).into(imageView);

        return imageView;
    }
}
