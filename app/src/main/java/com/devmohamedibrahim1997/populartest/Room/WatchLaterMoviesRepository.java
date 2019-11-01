package com.devmohamedibrahim1997.populartest.Room;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.devmohamedibrahim1997.populartest.model.MovieEntity;

import java.util.List;

class WatchLaterMoviesRepository {

    private WatchLaterMoviesDao movieDao;
    private LiveData<List<MovieEntity>> allMovies;

    WatchLaterMoviesRepository(Application application){
        WatchLaterMoviesDataBase movieDataBase = WatchLaterMoviesDataBase.getInstance(application);
        movieDao = movieDataBase.movieDao();
        allMovies = movieDao.getAllMovies();
    }

    void insert(MovieEntity movie){
        new InsertMovieAsyncTask(movieDao).execute(movie);
    }

    void delete(MovieEntity movie){
        new DeleteMovieAsyncTask(movieDao).execute(movie);
    }

    LiveData<List<MovieEntity>> getAllMovies() {
        return allMovies;
    }


    private static class InsertMovieAsyncTask extends AsyncTask<MovieEntity,Void,Void>{
        private WatchLaterMoviesDao movieDao;
        InsertMovieAsyncTask(WatchLaterMoviesDao movieDao){
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieEntity... movies) {
            movieDao.insertMovie(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<MovieEntity,Void,Void>{
        private WatchLaterMoviesDao movieDao;
        DeleteMovieAsyncTask(WatchLaterMoviesDao movieDao){
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieEntity... movies) {
            movieDao.deleteMovie(movies[0]);
            return null;
        }
    }
}
