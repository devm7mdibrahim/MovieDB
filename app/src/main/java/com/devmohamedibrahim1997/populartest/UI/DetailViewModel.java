package com.devmohamedibrahim1997.populartest.UI;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.devmohamedibrahim1997.populartest.model.CreditResponse;
import com.devmohamedibrahim1997.populartest.model.Movie;
import com.devmohamedibrahim1997.populartest.model.DetailsResponse;
import com.devmohamedibrahim1997.populartest.model.Videos;

import java.util.List;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<CreditResponse> movieCredit;
    private MutableLiveData<DetailsResponse> movieDetail;
    private MutableLiveData<List<Movie>> similarMovies;
    private MutableLiveData<List<Movie>> recommendedMovies;
    private MutableLiveData<List<Videos>> movieVideos;

    public void init(Integer movieId){
        Repository mRepository = Repository.getInstance(movieId);
        movieCredit = mRepository.getMovieCredit();
        movieDetail = mRepository.getMovieDetails();
        similarMovies = mRepository.getSimilarMovies();
        recommendedMovies = mRepository.getRecommendedMovies();
        movieVideos = mRepository.getMovieVideos();
    }

    MutableLiveData<CreditResponse> getMovieCredit() {
        return movieCredit;
    }

    MutableLiveData<DetailsResponse> getMovieDetails() {
        return movieDetail;
    }

    MutableLiveData<List<Movie>> getSimilarMovies() {
        return similarMovies;
    }

    MutableLiveData<List<Movie>> getRecommendedMovies() {
        return recommendedMovies;
    }

    MutableLiveData<List<Videos>> getMovieVideos() {
        return movieVideos;
    }
}
