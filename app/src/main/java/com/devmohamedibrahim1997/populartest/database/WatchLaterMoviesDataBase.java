package com.devmohamedibrahim1997.populartest.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.devmohamedibrahim1997.populartest.model.MovieEntity;

@Database(entities = MovieEntity.class,version = 7, exportSchema = false)
public abstract class WatchLaterMoviesDataBase extends RoomDatabase {

    private static WatchLaterMoviesDataBase instance;

    abstract WatchLaterMoviesDao movieDao();

    public static synchronized WatchLaterMoviesDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),WatchLaterMoviesDataBase.class,"watchLater_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
