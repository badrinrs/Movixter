package me.madri.movixter.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bnara on 3/18/2016.
 */
public class Movie {
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("genre_ids")
    private List<Integer> mGenreIds;
    @SerializedName("id")
    private int mId;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("populatity")
    private double mPopularity;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    public int getVoteCount() {
        return voteCount;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public List<Integer> getGenreIds() {
        return mGenreIds;
    }

    public int getId() {
        return mId;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public String getTitle() {
        return mTitle;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setPosterPath(String path) {
        mPosterPath = path;
    }
}
