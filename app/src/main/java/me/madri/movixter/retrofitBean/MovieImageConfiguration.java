package me.madri.movixter.retrofitBean;

import com.google.gson.annotations.SerializedName;

import me.madri.movixter.bean.MovieConfiguration;

/**
 * Created by bnara on 3/18/2016.
 */
public class MovieImageConfiguration {
    @SerializedName("images")
    private MovieConfiguration mMovieConfiguration;

    public MovieConfiguration getMovieConfiguration() {
        return mMovieConfiguration;
    }
}
