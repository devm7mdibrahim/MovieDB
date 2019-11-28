package com.devmohamedibrahim1997.populartest.repository;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.devmohamedibrahim1997.populartest.network.APIClient;
import com.devmohamedibrahim1997.populartest.network.MovieDBAPI;
import com.devmohamedibrahim1997.populartest.model.CreditResponse;
import com.devmohamedibrahim1997.populartest.model.Movie;
import com.devmohamedibrahim1997.populartest.model.DetailsResponse;
import com.devmohamedibrahim1997.populartest.model.MovieResponse;
import com.devmohamedibrahim1997.populartest.model.MovieVideos;
import com.devmohamedibrahim1997.populartest.model.Videos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Integer movieId;
    private MutableLiveData<CreditResponse> movieCredit = new MutableLiveData<>();
    private MutableLiveData<DetailsResponse> movieDetail = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> similarMovies = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> recommendedMovies = new MutableLiveData<>();
    private MutableLiveData<List<Videos>> movieVideos = new MutableLiveData<>();


    public static Repository getInstance(Integer mMovieId) {
        movieId = mMovieId;
        return new Repository();
    }

    public MutableLiveData<CreditResponse> getMovieCredit() {
        APIClient.getInstance()
                .create(MovieDBAPI.class)
                .getMovieCreditsResponse(movieId)
                .enqueue(new Callback<CreditResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CreditResponse> call, @NonNull Response<CreditResponse> response) {
                        if (response.body() != null) {
                            movieCredit.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CreditResponse> call, @NonNull Throwable t) {
                    }
                });
        return movieCredit;
    }

    public MutableLiveData<DetailsResponse> getMovieDetails() {
        APIClient.getInstance()
                .create(MovieDBAPI.class)
                .getMovieDetails(movieId)
                .enqueue(new Callback<DetailsResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<DetailsResponse> call, @NonNull Response<DetailsResponse> response) {
                        if (response.body() != null) {
                            movieDetail.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DetailsResponse> call, @NonNull Throwable t) {
                    }
                });
        return movieDetail;
    }

    public MutableLiveData<List<Movie>> getSimilarMovies() {
        APIClient.getInstance()
                .create(MovieDBAPI.class)
                .getSimilarResponse(movieId)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.body() != null) {
                            similarMovies.setValue(response.body().getMovies());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    }
                });
        return similarMovies;
    }

    public MutableLiveData<List<Movie>> getRecommendedMovies() {
        APIClient.getInstance()
                .create(MovieDBAPI.class)
                .getRecommendationResponse(movieId)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.body() != null) {
                            recommendedMovies.setValue(response.body().getMovies());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    }
                });
        return recommendedMovies;
    }

    public MutableLiveData<List<Videos>> getMovieVideos() {
        APIClient.getInstance()
                .create(MovieDBAPI.class)
                .getMovieVideosResponse(movieId)
                .enqueue(new Callback<MovieVideos>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieVideos> call, @NonNull Response<MovieVideos> response) {
                        if (response.body() != null) {
                            movieVideos.setValue(response.body().getVideos());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieVideos> call, @NonNull Throwable t) {

                    }
                });
        return movieVideos;
    }
}
