package com.heidritech.popularmovies;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

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
        getVideos();


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

    public String fetchVideoURL()
    {
        MovieObj movieObj = null;
        Intent intent = getActivity().getIntent();
        movieObj = (MovieObj) intent.getSerializableExtra("MovieObject");
        String url = baseTrailer + movieObj.getMovieID() + endTrailerURL;

        return url;
    }

    public String videoName(String videoName)
    {
        return videoName;
    }

    public void getVideos()
    {
        String url = fetchVideoURL();
        final ImageButton[] imageButton = new ImageButton[2];
        //final ImageButton imageButton1 = (ImageButton) getActivity().findViewById(R.id.relative_play1);
        final TextView[] textView = new TextView[2];
        //final TextView textView1 = (TextView) getActivity().findViewById(R.id.trailer_name1);



        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler()
        {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println(response);

                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("results");
                    System.out.println(jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        final String[] youtubeUrl = new String[jsonArray.length()];
                        JSONObject object = jsonArray.getJSONObject(i);
                        String type = object.getString("type");
                        if(type.equalsIgnoreCase("Trailer") )//|| type.equalsIgnoreCase("Teaser"))
                        {
                            String youtubeURL = "https://www.youtube.com/watch?v=";
                            String videoName = object.getString("name");
                            String videoKey = object.getString("key");
                            videoName = videoName(videoName);
                            youtubeURL += videoKey;


                            if (textView[0] == null && imageButton[0] == null)
                            {
                                youtubeUrl[0] = youtubeURL;
                                textView[0] = (TextView) getActivity().findViewById(R.id.trailer_name);
                                imageButton[0] = (ImageButton) getActivity().findViewById(R.id.relative_play);

                                System.out.println(videoName);
                                textView[0].setText(videoName);

                                imageButton[0].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl[0])));

                                    }
                                });

                            }

                            if (i > 0 && textView[0] != null && imageButton[0] != null)
                            {
                                youtubeUrl[1] = youtubeURL;
                                textView[1] = (TextView) getActivity().findViewById(R.id.trailer_name1);
                                imageButton[1] = (ImageButton) getActivity().findViewById(R.id.relative_play1);

                                textView[1].setText(videoName);
                                imageButton[1].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl[1])));

                                    }
                                });
                            }


                        }





                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}