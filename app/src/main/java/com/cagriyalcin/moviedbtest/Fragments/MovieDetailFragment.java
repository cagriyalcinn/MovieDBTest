package com.cagriyalcin.moviedbtest.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cagriyalcin.moviedbtest.Activities.MainActivity;
import com.cagriyalcin.moviedbtest.DataModels.MovieDetail;
import com.cagriyalcin.moviedbtest.R;
import com.cagriyalcin.moviedbtest.Retrofit.RetrofitClient;
import com.cagriyalcin.moviedbtest.Retrofit.ServiceCalls;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailFragment extends Fragment {

    ImageView imgMovie, imgIMDB;
    TextView tvRate, tvDate, tvMovieInfo, tvMovieTitle;
    int movieID;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        imgMovie = view.findViewById(R.id.imgMovie);
        imgIMDB = view.findViewById(R.id.imgIMDB);
        tvRate = view.findViewById(R.id.tvRate);
        tvDate = view.findViewById(R.id.tvDate);
        tvMovieInfo = view.findViewById(R.id.tvMovieInfo);
        tvMovieTitle = view.findViewById(R.id.tvMovieTitle);

        if (getArguments() != null) {
            movieID = getArguments().getInt("id");
            fetchMovieDetails();
        }

        return view;
    }

    private void fetchMovieDetails() {
        ServiceCalls serviceCalls = RetrofitClient.getAPIService();
        Map<String,String> hmap = new HashMap<>();
        hmap.put("language", MainActivity.appLanguage);
        hmap.put("api_key", MainActivity.apiKey);
        serviceCalls.getMovieDetails(movieID, hmap).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if(response.body() != null) {
                    Glide.with(getActivity()).load("https://image.tmdb.org/t/p/original/" + response.body().getPosterPath()).into(imgMovie);
                    tvRate.setText(getRate(response.body().getVoteAverage()));
                    tvDate.setText(response.body().getReleaseDate());
                    tvMovieTitle.setText(response.body().getTitle());
                    tvMovieInfo.setText(response.body().getOverview());

                    imgIMDB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/" + response.body().getImdbId()));
                            startActivity(browserIntent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {

            }
        });
    }

    private Spanned getRate(Double voteAverage) {
        String firstVote = String.valueOf(voteAverage);
        String vote = "<font color='#ADB5BD'>/10</font>";
        return Html.fromHtml(firstVote + vote);
    }
}