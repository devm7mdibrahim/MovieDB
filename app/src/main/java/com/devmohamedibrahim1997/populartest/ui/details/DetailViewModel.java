package com.devmohamedibrahim1997.populartest.ui.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.devmohamedibrahim1997.populartest.model.CreditResponse;
import com.devmohamedibrahim1997.populartest.model.Movie;
import com.devmohamedibrahim1997.populartest.model.DetailsResponse;
import com.devmohamedibrahim1997.populartest.model.Videos;
import com.devmohamedibrahim1997.populartest.repository.Repository;

import java.util.List;

public class DetailViewModel extends ViewModel {
    private LiveData<CreditResponse> movieCredit;
    private LiveData<DetailsResponse> movieDetail;
    private LiveData<List<Movie>> similarMovies;
    private LiveData<List<Movie>> recommendedMovies;
    private LiveData<List<Videos>> movieVideos;

    public void init(Integer movieId){
        Repository mRepository = Repository.getInstance(movieId);
        movieCredit = mRepository.getMovieCredit();
        movieDetail = mRepository.getMovieDetails();
        similarMovies = mRepository.getSimilarMovies();
        recommendedMovies = mRepository.getRecommendedMovies();
        movieVideos = mRepository.getMovieVideos();
    }

    LiveData<CreditResponse> getMovieCredit() {
        return movieCredit;
    }

    LiveData<DetailsResponse> getMovieDetails() {
        return movieDetail;
    }

    LiveData<List<Movie>> getSimilarMovies() {
        return similarMovies;
    }

    LiveData<List<Movie>> getRecommendedMovies() {
        return recommendedMovies;
    }

    LiveData<List<Videos>> getMovieVideos() {
        return movieVideos;
    }
}
