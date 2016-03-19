package me.madri.movixter.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bnara on 3/18/2016.
 */
public class MovieConfiguration {
    @SerializedName("base_url")
    private String mBaseUrl;
    @SerializedName("backdrop_sizes")
    private List<String> mBackdropSizes;
    @SerializedName("poster_sizes")
    private List<String> mPosterSizes;

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public List<String> getBackdropSizes() {
        return mBackdropSizes;
    }

    public List<String> getPosterSizes() {
        return mPosterSizes;
    }
}
