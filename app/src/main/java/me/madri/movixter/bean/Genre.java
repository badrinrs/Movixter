package me.madri.movixter.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bnara on 3/19/2016.
 */
public class Genre {
    @SerializedName("id")
    private int mGenreId;
    @SerializedName("name")
    private String mGenreName;

    public int getGenreId() {
        return mGenreId;
    }

    public String getGenreName() {
        return mGenreName;
    }
}
