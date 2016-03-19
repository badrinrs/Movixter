package me.madri.movixter.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.madri.movixter.R;
import me.madri.movixter.bgcolor.BgColor;

public class DetailActivity extends AppCompatActivity {
    @Bind(R.id.backdrop_image)
    ImageView mBackdropImage;
    @Bind(R.id.movie_or_tv_title)
    TextView mTitle;
    @Bind(R.id.genre_text)
    TextView mGenre;
    @Bind(R.id.overview_text)
    TextView mOverview;
    @Bind(R.id.release_date_text)
    TextView mReleaseDate;
    @Bind(R.id.popularity_text)
    TextView mPopularity;
    @Bind(R.id.vote_text)
    TextView mAvgVote;
    @Bind(R.id.total_votes_text)
    TextView mTotalVotes;
    @Bind(R.id.detail_layout)
    LinearLayout mDetailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActionBar()!=null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mDetailLayout.setBackgroundColor(new BgColor().getColor());
        Intent intent = getIntent();
        Resources resources = getResources();
        String backdropPath = intent.getStringExtra(resources.getString(R.string.backdrop_path_key));
        String title = intent.getStringExtra(resources.getString(R.string.title_key));
        String overview = intent.getStringExtra(resources.getString(R.string.overview_key));
        List<String> genreList = intent.getStringArrayListExtra(resources.getString(R.string.genre_list_key));
        String releaseDate = intent.getStringExtra(resources.getString(R.string.release_date_key));
        String popularity = intent.getStringExtra(resources.getString(R.string.popularity_key));
        String voteAverage = intent.getStringExtra(resources.getString(R.string.vote_average_key));
        String voteCount = intent.getStringExtra(resources.getString(R.string.total_votes_key));
        Picasso.with(getApplicationContext()).load(backdropPath).resize(1000, 1000).centerCrop().into(mBackdropImage);
        mTitle.setText(title);
        mOverview.setText(overview);
        mGenre.setText(TextUtils.join(",", genreList));
        mReleaseDate.setText(releaseDate);
        mPopularity.setText(popularity);
        mAvgVote.setText(voteAverage);
        mTotalVotes.setText(voteCount);
    }
}
