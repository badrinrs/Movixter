package me.madri.movixter.retrofitBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.madri.movixter.bean.Movie;

/**
 * Created by bnara on 3/18/2016.
 */
public class MovieResult {
    @SerializedName("page")
    private int mPage;
    @SerializedName("results")
    private List<Movie> mMovies;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int mTotalResults;

    public int getPage() {
        return mPage;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return mTotalResults;
    }
}
