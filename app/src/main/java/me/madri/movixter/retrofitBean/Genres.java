package me.madri.movixter.retrofitBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.madri.movixter.bean.Genre;

/**
 * Created by bnara on 3/19/2016.
 */
public class Genres {
    @SerializedName("genres")
    private List<Genre> mGenres;

    public List<Genre> getGenres() {
        return mGenres;
    }
}
