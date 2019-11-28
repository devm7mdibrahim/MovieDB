package com.devmohamedibrahim1997.populartest.database;

import com.devmohamedibrahim1997.populartest.model.MovieEntity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface WatchLaterMoviesDao {

    @Insert
    void insertMovie(MovieEntity movie);

    @Delete
    void deleteMovie(MovieEntity movie);

    @Query("SELECT * FROM watchLater_table ORDER BY mId ASC")
    LiveData<List<MovieEntity>> getAllMovies();
}
