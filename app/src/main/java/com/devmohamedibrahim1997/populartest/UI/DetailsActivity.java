package com.devmohamedibrahim1997.populartest.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.Room.WatchLaterMoviesViewModel;
import com.devmohamedibrahim1997.populartest.adapter.CastAdapter;
import com.devmohamedibrahim1997.populartest.adapter.GenreAdapter;
import com.devmohamedibrahim1997.populartest.adapter.RecommendedMovieAdapter;
import com.devmohamedibrahim1997.populartest.adapter.SimilarMovieAdapter;
import com.devmohamedibrahim1997.populartest.databinding.ActivityDetailsBinding;
import com.devmohamedibrahim1997.populartest.model.DetailsResponse;
import com.devmohamedibrahim1997.populartest.model.MovieEntity;
import com.devmohamedibrahim1997.populartest.model.Videos;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.isNetworkAvailable;
import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.showSnackBar;
import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.showToast;

public class DetailsActivity extends AppCompatActivity {


    ImageButton watchLaterImageButton;
    ImageButton videoPlayImageButton;
    ImageButton backImageButton;

    RecyclerView similarMoviesRecyclerView;
    RecyclerView recommendedMoviesRecyclerView;
    RecyclerView genresRecyclerView;
    RecyclerView castRecyclerView;


    private CastAdapter castAdapter;
    private SimilarMovieAdapter similarAdapter;
    private RecommendedMovieAdapter recommendedAdapter;
    private GenreAdapter genreAdapter;

    private DetailViewModel detailViewModel;
    private ActivityDetailsBinding detailsBinding;
    private boolean exists = false;
    private int movieId;
    private ArrayList<String> videosKeysArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        init();
        getMovieIntent();
        initViewModel();
        getDetails(savedInstanceState);
        detailsBinding.detailSwipeRefresh.setOnRefreshListener(() -> getDetails(null));

    }

    //get clicked movie id
    private void getMovieIntent() {
        movieId = getIntent().getIntExtra("movieId", 0);
    }

    private void init() {
        similarMoviesRecyclerView = detailsBinding.detailSimilarRecyclerView;
        recommendedMoviesRecyclerView = detailsBinding.detailRecommendationRecyclerView;
        genresRecyclerView = detailsBinding.detailGenresRecyclerView;
        castRecyclerView = detailsBinding.detailCastRecyclerView;
        watchLaterImageButton = detailsBinding.detailWatchLaterImageButton;
        videoPlayImageButton = detailsBinding.detailVideoPlayImageButton;
        backImageButton = detailsBinding.detailBackImageButton;

        initRecyclersView();

        watchLaterImageButton.setOnClickListener(view -> onWatchLaterImageClicked());
        videoPlayImageButton.setOnClickListener(view -> onVideoPlayImageClicked());
        backImageButton.setOnClickListener(view -> finish());
    }

    //init recyclers (genre, cast, similar, recommended)
    private void initRecyclersView(){
        //genre recyclerView
        genresRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        genreAdapter = new GenreAdapter(this);
        genresRecyclerView.setAdapter(genreAdapter);

        //cast recyclerView
        castRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        castAdapter = new CastAdapter(this);
        castRecyclerView.setAdapter(castAdapter);

        //similar movies recyclerView
        similarMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        similarAdapter = new SimilarMovieAdapter(this);
        similarMoviesRecyclerView.setAdapter(similarAdapter);

        //recommended recyclerView
        recommendedMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recommendedAdapter = new RecommendedMovieAdapter(this);
        recommendedMoviesRecyclerView.setAdapter(recommendedAdapter);

    }

    //init viewModel
    private void initViewModel() {
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
    }

    //get details
    private void getDetails(Bundle savedInstanceState){
        if (isNetworkAvailable(DetailsActivity.this)) {
            if (savedInstanceState == null) {
                detailViewModel.init(movieId);
                getMovieDetails();
                getMovieVideos();
                getMovieCredit();
                getSimilarMovies();
                onSimilarMoviesRecyclerViewItemClicked();
                getRecommendedMovies();
                onRecommendedMoviesRecyclerViewItemClicked();
            }
        } else {
            showSnackBar(DetailsActivity.this);
            detailsBinding.detailProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void getMovieDetails() {
        detailViewModel.getMovieDetails().observe(this, detailsResponse -> {
            if (detailsResponse != null) {
                detailsBinding.detailSwipeRefresh.setRefreshing(false);
                detailsBinding.detailProgressBar.setVisibility(View.INVISIBLE);
                detailsBinding.setMovieDetails(detailsResponse);
                genreAdapter.setData(detailsResponse.getGenres());
                genreAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getMovieCredit() {
        detailViewModel.getMovieCredit().observe(this, creditResponse -> {
            if (creditResponse != null) {
                if (creditResponse.getCast() != null) {
                    castAdapter.setCast(creditResponse.getCast());
                    castAdapter.notifyDataSetChanged();
                }

                if (creditResponse.getCrew() != null) {
                    try {
                        detailsBinding.setCrewDirector(creditResponse.getCrew().get(0).getName());
                        if (creditResponse.getCrew().size() >= 2) {
                            detailsBinding.setCrewWriters(creditResponse.getCrew().get(1).getName());
                        }
                    }catch (IndexOutOfBoundsException e){
                        Log.e("exception", e.getMessage() );
                    }

                }
            }
        });
    }

    private void getSimilarMovies() {
        detailViewModel.getSimilarMovies().observe(this, movies -> {
            similarAdapter.setData(movies);
            similarAdapter.notifyDataSetChanged();
        });
    }

    private void onSimilarMoviesRecyclerViewItemClicked() {
        similarAdapter.setOnItemClickListener((position, v) -> {
            Intent intent = new Intent(DetailsActivity.this, DetailsActivity.class);
            if (detailViewModel.getSimilarMovies().getValue() != null) {
                intent.putExtra("movieId", detailViewModel.getSimilarMovies().getValue().get(position).getId());
            }
            startActivity(intent);
        });
    }

    private void getRecommendedMovies() {
        detailViewModel.getRecommendedMovies().observe(this, movies -> {
            recommendedAdapter.setData(movies);
            recommendedAdapter.notifyDataSetChanged();
        });
    }

    private void onRecommendedMoviesRecyclerViewItemClicked() {
        recommendedAdapter.setOnItemClickListener((position, v) -> {
            Intent intent = new Intent(DetailsActivity.this, DetailsActivity.class);
            if (detailViewModel.getRecommendedMovies().getValue() != null) {
                intent.putExtra("movieId", detailViewModel.getRecommendedMovies().getValue().get(position).getId());
            }
            startActivity(intent);
        });
    }

    private void getMovieVideos() {
        detailViewModel.getMovieVideos().observe(this, videos -> {
            if (videos != null) {
                try {
                    for(Videos video : videos){
                        videosKeysArrayList.add(video.getKey());
                    }
                }catch (IndexOutOfBoundsException e){
                    videoPlayImageButton.setVisibility(View.GONE);
                }
            }
        });

    }

    public void onVideoPlayImageClicked() {
        //send movieKeys if its not null
        if(videosKeysArrayList != null) {
            Intent intent = new Intent(DetailsActivity.this, VideoPlayerActivity.class);
            intent.putExtra("videosKeysArrayList",videosKeysArrayList);
            startActivity(intent);
        }
    }

    public void onWatchLaterImageClicked() {

        if (detailViewModel.getMovieDetails().getValue() != null) {

            final DetailsResponse movie = detailViewModel.getMovieDetails().getValue();

            final WatchLaterMoviesViewModel movieViewModel = ViewModelProviders.of(this).get(WatchLaterMoviesViewModel.class);

            movieViewModel.getAllMovies().observe(DetailsActivity.this, movieEntities -> {
                if (movieEntities != null) {
                    for (MovieEntity m : movieEntities) {
                        if (m.getId().equals(movie.getId())) {
                            exists = true;
                        }
                    }
                }

                if (!exists) {
                    movieViewModel.insert(new MovieEntity(movie.getId(), movie.getVoteAverage(), movie.getTitle(),
                            movie.getPosterPath(), movie.getOverview(), movie.getReleaseDate()));
                    showToast(DetailsActivity.this, "Movie added to watch later ");
                }
            });
        }

    }
}
