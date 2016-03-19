package me.madri.movixter.retrofit;

import java.util.Map;

import me.madri.movixter.retrofitBean.Genres;
import me.madri.movixter.retrofitBean.MovieImageConfiguration;
import me.madri.movixter.retrofitBean.MovieResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by bnara on 3/18/2016.
 */
public interface MovieDbService {
    @GET("/3/configuration")
    Call<MovieImageConfiguration> getConfiguration(@Query("api_key") String apiKey);
    @GET("/3/discover/{movie_or_tv}")
    Call<MovieResult> discover(@Path("movie_or_tv") String movieOrTv,
                               @Query("api_key") String apiKey,
                               @QueryMap Map<String, String> queryMap);
    @GET("/3/genre/{movie_or_tv}/list")
    Call<Genres> getGenreList(@Path("movie_or_tv") String movieOrTv,
                              @Query("api_key") String apiKey);
}
