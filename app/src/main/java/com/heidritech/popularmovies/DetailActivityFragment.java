package com.heidritech.popularmovies;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.square.android.expandabletextview.ExpandableTextView;
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
    private String imageBaseUrl = "http://image.tmdb.org/t/p/w780/";
    MovieObj intentMovie = null;
    String baseApi = "http://api.themoviedb.org/3/movie/";
    String endTrailerURL = "/videos?api_key=13ebc35e0c6a99a673ac605b5e7f3710";
    String endReviewsURL = "/reviews?api_key=13ebc35e0c6a99a673ac605b5e7f3710";
    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        getVideos();
        fetchReviews();


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
        Picasso.with(getActivity()).load(imageBaseUrl + intentMovie.getBackdrop_path()).into(imageView);


        return rootView;
    }


    public String fetchVideoURL()
    {
        MovieObj movieObj = null;
        Intent intent = getActivity().getIntent();
        movieObj = (MovieObj) intent.getSerializableExtra("MovieObject");
        String url = baseApi + movieObj.getMovieID() + endTrailerURL;

        return url;
    }

    public String fetchReviewsURL()
    {
        MovieObj movieObj = null;
        Intent intent = getActivity().getIntent();
        movieObj = (MovieObj) intent.getSerializableExtra("MovieObject");
        String url = baseApi + movieObj.getMovieID() + endReviewsURL;

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
                Boolean isSet = false;
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("results");
                    System.out.println(jsonArray);
                    if (jsonArray == null)
                    {
                        return;
                    }
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        textView[1] = (TextView) getActivity().findViewById(R.id.trailer_name1);
                        imageButton[1] = (ImageButton) getActivity().findViewById(R.id.relative_play1);
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

                                isSet = true;

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

                                if (textView[0].getText() == textView[1].getText()){
                                    isSet = false;
                                }
                            }
                            if(!isSet)
                                {
                                    LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.linearlayout);
                                    /*View view = getActivity().findViewById(R.id.hline3);*/


                                    layout.removeView(imageButton[1]);
                                    layout.removeView(textView[1]);
                                    /*layout.removeView(view);*/
                                }
                            else if (jsonArray.length() == 1)
                                    {
                                        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.linearlayout);
                                        /*View view = getActivity().findViewById(R.id.hline3);*/


                                        layout.removeView(imageButton[1]);
                                        layout.removeView(textView[1]);
                                        /*layout.removeView(view);*/
                                    }

                            else if (jsonArray == null)
                            {
                                LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.linearlayout);
                                LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.trailer_layout);
                                /*View view = getActivity().findViewById(R.id.hline3);*/
                                View view1 = getActivity().findViewById(R.id.hline2);


                                linearLayout.removeView(imageButton[0]);
                                linearLayout.removeView(textView[0]);
                                linearLayout.removeView(view1);
                                layout.removeView(imageButton[1]);
                                layout.removeView(textView[1]);
                                /*layout.removeView(view);*/
                            }

                            System.out.println("This is i: " + i);

                        }





                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fetchReviews()
    {
        String url = fetchReviewsURL();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONArray jsonArray = null;

                try {
                    jsonArray = response.getJSONArray("results");
                    System.out.println(jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        /*TextView authorTextview = (TextView) getActivity().findViewById(R.id.author);
                        TextView contentTextview = (TextView) getActivity().findViewById(R.id.content);*/
                        ExpandableTextView textView = (ExpandableTextView) getActivity().findViewById(R.id.review1).findViewById(R.id.expand_text_view);
                        TextView textView1 = (TextView) getActivity().findViewById(R.id.review1).findViewById(R.id.title);
                        JSONObject object = jsonArray.getJSONObject(i);
                        String content = object.getString("content");
                        String author = object.getString("author");
                        System.out.println(content);

                        if (object != null) {/*
                            authorTextview.setText(author);
                            contentTextview.setText(content);*/
                            textView.setText(content);
                            textView1.setText(author);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }




}