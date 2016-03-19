package me.madri.movixter.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.madri.movixter.R;
import me.madri.movixter.bean.Movie;
import me.madri.movixter.bgcolor.BgColor;
import me.madri.movixter.ui.DetailActivity;

/**
 * Created by bnara on 3/17/2016.
 */
public class DiscoverMoviesAdapter extends BaseAdapter {

    private static String TAG = DiscoverMoviesAdapter.class.getSimpleName();

    private Context mContext;
    private Movie[] mMovies;

    public DiscoverMoviesAdapter(Context context, Movie[] movies) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public int getCount() {
        return mMovies.length;
    }

    @Override
    public Movie getItem(int position) {
        return mMovies[position];
    }

    @Override
    public long getItemId(int position) {
        return mMovies[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        Movie movie = getItem(position);
        Log.v(TAG, "Poster Path Url: "+movie.getPosterPath());
        Log.v(TAG, "Convert View: "+convertView);
        MovieHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.movie, null);
            holder = new MovieHolder(gridView);
            gridView.setTag(holder);
        } else {
            holder = (MovieHolder) gridView.getTag();
        }
        Picasso.with(mContext).load(movie.getPosterPath()).into(holder.mImageView);
        holder.mImageView.setContentDescription(movie.getTitle());
        holder.mTextView.setText(movie.getTitle());
        return gridView;
    }

    static class MovieHolder {
        public ImageView mImageView;
        public TextView mTextView;
        public MovieHolder(View base) {
            mImageView = (ImageView) base.findViewById(R.id.movieImage);
            mTextView = (TextView) base.findViewById(R.id.movieName);
        }
    }
}

