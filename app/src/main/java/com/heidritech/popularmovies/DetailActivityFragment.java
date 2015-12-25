package com.heidritech.popularmovies;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private ExpandableTextView overview;
    private ImageView poster;
    private TextView releaseDate;
    private TextView title;
    private TextView userRating;
    private ImageView imageView;
    private ImageButton favoriteButton;
    private String posterImageBaseURL = "http://image.tmdb.org/t/p/w185";
    private String imageBaseUrl = "http://image.tmdb.org/t/p/w500/";
    MovieObj intentMovie = null;
    String api_key = "?api_key=13ebc35e0c6a99a673ac605b5e7f3710";
    String baseApi = "http://api.themoviedb.org/3/movie/";
    String endTrailerURL = "/videos?api_key=13ebc35e0c6a99a673ac605b5e7f3710";
    String endReviewsURL = "/reviews?api_key=13ebc35e0c6a99a673ac605b5e7f3710";
    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        final MovieDBHelper openHelper = new MovieDBHelper(getActivity());
        final SQLiteDatabase database = openHelper.getWritableDatabase();

        getVideos();
        /*fetchReviews();*/
        getMovieRuntime();





        final Intent intent = getActivity().getIntent();
        intentMovie = (MovieObj) intent.getSerializableExtra("MovieObject");
        /*System.out.println(intentMovie.getGenres());*/


        overview = (ExpandableTextView) rootView.findViewById(R.id.movie_overview).findViewById(R.id.expand_text_view);
        releaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
        title = (TextView) rootView.findViewById(R.id.movie_title);
        userRating = (TextView) rootView.findViewById(R.id.movie_user_rating);
        imageView = (ImageView) rootView.findViewById(R.id.imageView2);
        favoriteButton = (ImageButton) rootView.findViewById(R.id.favorites_button);
        poster = (ImageView) rootView.findViewById(R.id.detail_poster);



        overview.setText(intentMovie.getOverview());
        title.setText(intentMovie.getOriginal_title());
        releaseDate.setText(formattedDate(intentMovie.getRelease_date()));
        userRating.setText(intentMovie.getVote_average());


        Picasso.with(getActivity()).load(imageBaseUrl + intentMovie.getBackdrop_path()).into(imageView);
        Picasso.with(getActivity()).load(posterImageBaseURL + intentMovie.getPoster_path()).into(poster);


        if (openHelper.isFavorite(intentMovie))
        {
            favoriteButton.setImageResource(R.drawable.star);
        }
        else
        {
            favoriteButton.setImageResource(R.drawable.star_outline);
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!openHelper.isFavorite(intentMovie)) {
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(MovieContract.columns.MOVIE_ID, intentMovie.getMovieID());
                    contentValues.put(MovieContract.columns.COL_TITLE, intentMovie.getOriginal_title());
                    contentValues.put(MovieContract.columns.COL_BACKDROP, intentMovie.getBackdrop_path());
                    contentValues.put(MovieContract.columns.COL_POSTER, intentMovie.getPoster_path());
                    contentValues.put(MovieContract.columns.COL_RELEASE, intentMovie.getRelease_date());
                    contentValues.put(MovieContract.columns.COL_VOTE, intentMovie.getVote_average());
                    contentValues.put(MovieContract.columns.COL_OVERVIEW, intentMovie.getOverview());

                    database.insert(MovieContract.columns.TABLE_NAME, null, contentValues);
                    favoriteButton.setImageResource(R.drawable.star);

                    Toast.makeText(getContext(), "Successfully Added " + intentMovie.getOriginal_title() + " To Favorites! ", Toast.LENGTH_SHORT).show();
                }

                else if (openHelper.isFavorite(intentMovie))
                {
                    favoriteButton.setImageResource(R.drawable.star_outline);
                    int deleteRows = database.delete(MovieContract.columns.TABLE_NAME, MovieContract.columns.MOVIE_ID + "= ?", new String[]{intentMovie.getMovieID()});

                        Context context = getContext();
                        Toast.makeText(context, "Successfully Removed " + intentMovie.getOriginal_title() + " From Favorites!", Toast.LENGTH_SHORT).show();

                    System.out.println("Deleted " + deleteRows + " records.");
                }
            }
        });

        return rootView;
    }

    public String formattedDate(String date)
    {
        String formattedDate;
        int hyp2 = date.lastIndexOf('-');
        String year = date.substring(0,4);
        String month = date.substring(5, hyp2);

        switch(month){
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
        }

        formattedDate = month + " " + year;

        return formattedDate;
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

    public String fetchMovieInfoURL()
    {
        MovieObj obj = null;
        Intent intent = getActivity().getIntent();
        obj = (MovieObj) intent.getSerializableExtra("MovieObject");
        String url = baseApi + obj.getMovieID() + api_key;

        return url;
    }

    public void getMovieRuntime()
    {
        String url = fetchMovieInfoURL();
        final TextView [] textView = new TextView[1];

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONArray jsonArray = null;

                try {
                    String runtime = response.getString("runtime");
                    System.out.println("Runtime: " + runtime);
                    textView[0] = (TextView) getActivity().findViewById(R.id.runtime);
                    textView[0].setText(runtime + " Minutes");

                    jsonArray = response.getJSONArray("genres");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.movie_overview).findViewById(R.id.content_linear);
                        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        linearLayout.addView(relativeLayout);


                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject object = jsonArray.getJSONObject(i);

                        String idKey = object.getString("id");
                        String valueName = object.getString("name");

                        map.put(idKey, valueName);

                        ImageButton imageButton = new ImageButton(getActivity());
                        TextView textView1 = new TextView(getActivity());
                        imageButton.setImageResource(movieGenreIcon(map));
                        imageButton.setId(Integer.parseInt(idKey));
                        imageButton.setBackground(null);
                        relativeLayout.addView(imageButton);

                        textView1.setText(valueName);
                        textView1.setTextAppearance(getActivity(), android.R.style.TextAppearance_Holo_Small);
                        layoutParams.addRule(RelativeLayout.BELOW, imageButton.getId());
                        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, imageButton.getId());
                        textView1.setLayoutParams(layoutParams);
                        relativeLayout.addView(textView1);


                        System.out.println("Map: " + map);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public static int movieGenreIcon(HashMap<String,String> ID)
    {
        if (ID.containsKey("28")){
            return R.drawable.action;
        } else if (ID.containsKey("12")){
            return R.drawable.adventures50;
        } else if (ID.containsKey("16")){
            return R.drawable.animation50;
        } else if (ID.containsKey("35")){
            return R.drawable.comedy;
        } else if (ID.containsKey("80")){
            return R.drawable.crime;
        } else if (ID.containsKey("99")){
            return R.drawable.documentary;
        } else if (ID.containsKey("18")){
            return R.drawable.drama50;
        } else if (ID.containsKey("10751")){
            return R.drawable.family;
        } else if (ID.containsKey("14")){
            return R.drawable.fantasy50;
        } else if (ID.containsKey("10769")){
            return R.drawable.stevejobs50;
        } else if (ID.containsKey("53")) {
            return R.drawable.thriller50;
        } else if (ID.containsKey("36")) {
            return R.drawable.historical;
        } else if (ID.containsKey("27")){
            return R.drawable.horror;
        } else if (ID.containsKey("10402")){
            return R.drawable.musical50;
        } else if (ID.containsKey("9648")) {
            return R.drawable.spy50;
        } else if (ID.containsKey("10749")) {
            return R.drawable.novel50;
        } else if (ID.containsKey("878")){
            return R.drawable.scifi50;
        } else if (ID.containsKey("37")){
            return R.drawable.western50;
        }

        return -1;
    }

    public String runTime(String runtime)
    {
        return runtime;
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
                            /*if(!isSet)
                                {
                                    LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.linearlayout);
                                    *//*View view = getActivity().findViewById(R.id.hline3);*//*


                                    layout.removeView(imageButton[1]);
                                    layout.removeView(textView[1]);
                                    *//*layout.removeView(view);*//*
                                }
                            else if (jsonArray.length() == 1)
                                    {
                                        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.linearlayout);
                                        *//*View view = getActivity().findViewById(R.id.hline3);*//*


                                        layout.removeView(imageButton[1]);
                                        layout.removeView(textView[1]);
                                        *//*layout.removeView(view);*//*
                                    }

                            else if (jsonArray == null)
                            {
                                LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.linearlayout);
                               *//* LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.trailer_layout);*//*
                                *//*View view = getActivity().findViewById(R.id.hline3);*//*



                                *//*linearLayout.removeView(imageButton[0]);
                                linearLayout.removeView(textView[0]);*//*
                                layout.removeView(imageButton[1]);
                                layout.removeView(textView[1]);
                                *//*layout.removeView(view);*//*
                            }*/

                            System.out.println("This is i: " + i);

                        }





                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*public void fetchReviews()
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
                        *//*TextView authorTextview = (TextView) getActivity().findViewById(R.id.author);
                        TextView contentTextview = (TextView) getActivity().findViewById(R.id.content);*//*
                        ExpandableTextView textView = (ExpandableTextView) getActivity().findViewById(R.id.review1).findViewById(R.id.expand_text_view);
                        TextView textView1 = (TextView) getActivity().findViewById(R.id.review1).findViewById(R.id.title);
                        JSONObject object = jsonArray.getJSONObject(i);
                        String content = object.getString("content");
                        String author = object.getString("author");
                        System.out.println(content);

                        if (object != null) {*//*
                            authorTextview.setText(author);
                            contentTextview.setText(content);*//*
                            textView.setText(content);
                            textView1.setText(author);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/




}