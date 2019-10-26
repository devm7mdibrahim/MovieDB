package com.devmohamedibrahim1997.populartest.Room;

import android.app.Application;

import com.devmohamedibrahim1997.populartest.model.MovieEntity;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

public class WatchLaterMoviesViewModel extends AndroidViewModel {

    private WatchLaterMoviesRepository movieRepository;
    private LiveData<List<MovieEntity>> allMovies;

    public WatchLaterMoviesViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new WatchLaterMoviesRepository(application);
        allMovies = movieRepository.getAllMovies();
    }

    public void insert(MovieEntity movie){
        movieRepository.insert(movie);
    }

    public void delete(MovieEntity movie){
        movieRepository.delete(movie);
    }

    public LiveData<List<MovieEntity>> getAllMovies() {
        return allMovies;
    }
}
