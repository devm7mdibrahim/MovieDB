package com.devmohamedibrahim1997.populartest.UI;

import com.devmohamedibrahim1997.populartest.model.Movie;
import com.devmohamedibrahim1997.populartest.paging.MoviesDataSourceFactory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import static com.devmohamedibrahim1997.populartest.Utils.Constant.PAGE_SIZE;

public class MovieViewModel extends ViewModel {

    private PagedList.Config config;
    private LiveData liveData;
    private MutableLiveData<Boolean> progressBarLoading = new MutableLiveData<>();

    public MovieViewModel(){
        config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();
    }

    public LiveData<PagedList<Movie>> getMovies(String query){
        if (liveData == null ) {
            MoviesDataSourceFactory sourceFactory = new MoviesDataSourceFactory(query);
            liveData = new LivePagedListBuilder<>(sourceFactory, config).build();
        }
        return liveData;
    }

    public void setLiveData(){
        liveData =null;
    }

}
