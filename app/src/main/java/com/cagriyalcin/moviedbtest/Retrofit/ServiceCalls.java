package com.cagriyalcin.moviedbtest.Retrofit;

import com.cagriyalcin.moviedbtest.DataModels.MovieDetail;
import com.cagriyalcin.moviedbtest.DataModels.NowPlayingModel;
import com.cagriyalcin.moviedbtest.DataModels.UpComingMovieModel;
import com.google.gson.JsonObject;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ServiceCalls {
    @Headers("Content-Type: application/json")
    @GET("/3/movie/upcoming")
    Call<UpComingMovieModel> getUpComingMovies(@QueryMap() Map<String,String> parameters);

    @Headers("Content-Type: application/json")
    @GET("/3/movie/now_playing")
    Call<NowPlayingModel> getNowPlayingMovies(@QueryMap() Map<String,String> parameters);

    @Headers("Content-Type: application/json")
    @GET("/3/movie/{movie_id}")
    Call<MovieDetail> getMovieDetails(@Path("movie_id") int movie_id, @QueryMap() Map<String,String> parameters);
}
