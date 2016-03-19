package me.madri.movixter.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.madri.movixter.R;
import me.madri.movixter.adapter.DiscoverMoviesAdapter;
import me.madri.movixter.bean.Genre;
import me.madri.movixter.bean.Movie;
import me.madri.movixter.bean.MovieConfiguration;
import me.madri.movixter.bgcolor.BgColor;
import me.madri.movixter.retrofit.MovieDbService;
import me.madri.movixter.retrofitBean.Genres;
import me.madri.movixter.retrofitBean.MovieImageConfiguration;
import me.madri.movixter.retrofitBean.MovieResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Movie[] mMovies;
    private MovieConfiguration mMovieConfiguration;
    private GridView mGridView;
    private Map<Integer, String> mMovieGenreMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGridView = (GridView) findViewById(R.id.listView);
                discoverMovies();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.movies_layout);
        BgColor bgColor = new BgColor();
        int color = bgColor.getColor();
        assert relativeLayout != null;
        relativeLayout.setBackgroundColor(color);
    }

    private void discoverMovies() {
        final Resources resources = getResources();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(resources.getString(R.string.tmdb_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final MovieDbService movieDbService = retrofit.create(MovieDbService.class);
        Call<MovieImageConfiguration> movieImageConfigurationCall = movieDbService
                .getConfiguration(resources.getString(R.string.tmdb_api_key));
        movieImageConfigurationCall.enqueue(new Callback<MovieImageConfiguration>() {
            @Override
            public void onResponse(Call<MovieImageConfiguration> call,
                                   Response<MovieImageConfiguration> response) {
                Log.v(TAG, "Config Call Response Code: " + response.code());
                if(response.code()==200) {
                    MovieImageConfiguration movieImageConfiguration = response.body();
                    mMovieConfiguration = movieImageConfiguration.getMovieConfiguration();
                    Log.v(TAG, "Base Image Url: " + mMovieConfiguration.getBaseUrl());
                    mMovieGenreMap = new HashMap<>();
                    Call<Genres> movieGenresCall = movieDbService.getGenreList("movie", resources.getString(R.string.tmdb_api_key));
                    movieGenresCall.enqueue(new Callback<Genres>() {
                        @Override
                        public void onResponse(Call<Genres> call, Response<Genres> response) {
                            Genres movieGenres = response.body();
                            List<Genre> movieGenreList = movieGenres.getGenres();
                            for(Genre genre : movieGenreList) {
                                mMovieGenreMap.put(genre.getGenreId(), genre.getGenreName());
                            }
                            Call<MovieResult> movieResultCall = movieDbService.discover("movie",
                                    resources.getString(R.string.tmdb_api_key), new HashMap<String, String>());
                            movieResultCall.enqueue(new Callback<MovieResult>() {
                                @Override
                                public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                                    Log.v(TAG, "Discover Movie Call Response Code: " + response.code());
                                    MovieResult movieResult = response.body();
                                    List<Movie> movies = movieResult.getMovies();
                                    mMovies = new Movie[movies.size()];
                                    Display display = getWindowManager().getDefaultDisplay();
                                    Point size = new Point();
                                    display.getSize(size);
                                    int width = size.x;
                                    int expectedPosterSize = width / 3;
                                    int posterSize = 92;
                                    for (String posterSizeText : mMovieConfiguration.getPosterSizes()) {
                                        if (!posterSizeText.equals("original")) {
                                            int curPosterSize = Integer.parseInt(posterSizeText.split("w")[1]);
                                            if (curPosterSize < expectedPosterSize) {
                                                posterSize = curPosterSize;
                                            }
                                        }
                                    }
                                    for (int i = 0; i < movies.size(); i++) {
                                        mMovies[i] = movies.get(i);
                                        String path = mMovieConfiguration.getBaseUrl() + "w" + posterSize + mMovies[i].getPosterPath();
                                        mMovies[i].setPosterPath(path);
                                    }
                                    Log.v(TAG, "Movie Title: " + mMovies[0].getTitle());
                                    DiscoverMoviesAdapter moviesAdapter = new DiscoverMoviesAdapter(getApplicationContext(), mMovies);
                                    moviesAdapter.notifyDataSetChanged();
                                    mGridView.setAdapter(moviesAdapter);
                                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                            intent.putExtra(resources.getString(R.string.overview_key), mMovies[position].getOverview());
                                            intent.putExtra(resources.getString(R.string.title_key), mMovies[position].getTitle());
                                            intent.putExtra(resources.getString(R.string.release_date_key), mMovies[position].getReleaseDate());
                                            String backdropUrl = mMovieConfiguration.getBaseUrl()+mMovieConfiguration.getBackdropSizes().get(3)+mMovies[position].getBackdropPath();
                                            intent.putExtra(resources.getString(R.string.backdrop_path_key), backdropUrl);
                                            ArrayList<String> genreList = new ArrayList<>();
                                            List<Integer> genreIds = mMovies[position].getGenreIds();
                                            for(int genreId : genreIds) {
                                                genreList.add(mMovieGenreMap.get(genreId));
                                            }
                                            intent.putStringArrayListExtra(resources.getString(R.string.genre_list_key), genreList);
                                            intent.putExtra(resources.getString(R.string.vote_average_key), Double.toString(mMovies[position].getVoteAverage()));
                                            intent.putExtra(resources.getString(R.string.popularity_key), Double.toString(mMovies[position].getPopularity()));
                                            intent.putExtra(resources.getString(R.string.total_votes_key), Integer.toString(mMovies[position].getVoteCount()));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            getApplicationContext().startActivity(intent);
                                        }
                                    });
                                    Log.v(TAG, "List View: " + mGridView);
                                    Log.v(TAG, "Count of size: " + moviesAdapter.getCount());

                                }

                                @Override
                                public void onFailure(Call<MovieResult> call, Throwable t) {
                                    Log.e(TAG, "Failed Discover Movie Call: " + t.getMessage());
                                }
                            });
                        }
                        @Override
                        public void onFailure(Call<Genres> call, Throwable t) {
                            Log.e(TAG, "Failed Movie Genre List Call: "+t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<MovieImageConfiguration> call, Throwable t) {
                Log.e(TAG, "Failed Config Call: " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.search_movies) {

        } else if (id == R.id.discover_popular_movies) {

        } else if (id == R.id.discover_popular_tv) {

        } else if (id == R.id.search_tv) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
