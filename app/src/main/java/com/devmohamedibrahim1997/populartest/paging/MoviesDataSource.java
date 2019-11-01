package com.devmohamedibrahim1997.populartest.paging;

import androidx.paging.PageKeyedDataSource;
import androidx.annotation.NonNull;
import com.devmohamedibrahim1997.populartest.NetWork.APIClient;
import com.devmohamedibrahim1997.populartest.NetWork.MovieDBAPI;
import com.devmohamedibrahim1997.populartest.model.Movie;
import com.devmohamedibrahim1997.populartest.model.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.devmohamedibrahim1997.populartest.Utils.Constant.FIRST_PAGE;

public class MoviesDataSource extends PageKeyedDataSource<Integer, Movie> {

    private String query;
    private MovieDBAPI movieDBAPI;


    MoviesDataSource(String query){
        this.query = query;
        movieDBAPI = APIClient.getInstance().create(MovieDBAPI.class);
    }

    @Override
    public void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie> callback) {
        getMoviesResponseCall(FIRST_PAGE)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().getMovies(), null, FIRST_PAGE+1);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
        getMoviesResponseCall(params.key)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {

                        MovieResponse body = response.body();
                        if (body != null) {
                            Integer key = params.key <= body.getTotalPages()? params.key +1 : null;
                            callback.onResult(body.getMovies(), key);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    }
                });
    }

    private Call<MovieResponse> getMoviesResponseCall(int pageNumber) {
        return (query.equals("now_playing") || query.equals("popular") ||
                query.equals("top_rated") || query.equals("upcoming"))?
                movieDBAPI.getMovieResponse(query,pageNumber) : movieDBAPI.getSearchResponse(query,pageNumber);
    }
}
