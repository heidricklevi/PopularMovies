package com.heidritech.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Levi on 7/28/2015.
 */
public class ImageAdapter extends BaseAdapter {
    Context mContext;

    public ImageAdapter(Context context)
    {
        mContext = context;
    }

    @Override
    public int getCount() {
        return imgId.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        View rowView;
        LayoutInflater inflater = ( LayoutInflater )mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            rowView = inflater.inflate(R.layout.image_cell, null);
            imageView = (ImageView) rowView.findViewById(R.id.imageView1);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imgId[position]);
        return imageView;
    }

    private int [] imgId =
            {
                R.drawable.no_poseter_found,R.drawable.sample_2, R.drawable.sample_3,
                    R.drawable.sample_4, R.drawable.sample_5,
                    R.drawable.sample_6, R.drawable.sample_7,
                    R.drawable.sample_0, R.drawable.sample_1,
                    R.drawable.sample_2, R.drawable.sample_3,
                    R.drawable.sample_4, R.drawable.sample_5,
                    R.drawable.sample_6, R.drawable.sample_7,
                    R.drawable.sample_0, R.drawable.sample_1,
                    R.drawable.sample_2, R.drawable.sample_3,
                    R.drawable.sample_4, R.drawable.sample_5,
                    R.drawable.sample_6, R.drawable.sample_7
            };
}
