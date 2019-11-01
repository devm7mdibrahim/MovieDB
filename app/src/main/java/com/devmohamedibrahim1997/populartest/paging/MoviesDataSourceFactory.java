package com.devmohamedibrahim1997.populartest.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class MoviesDataSourceFactory extends DataSource.Factory {

    private String query;
    private MoviesDataSource moviesDataSource;

    public MoviesDataSourceFactory(String query){
        this.query = query;
    }

    @NonNull
    @Override
    public DataSource create() {
        if(moviesDataSource == null) {
            moviesDataSource = new MoviesDataSource(query);
        }
        return moviesDataSource;
    }
}
