package com.devmohamedibrahim1997.populartest.NetWork;

import com.devmohamedibrahim1997.populartest.model.CreditResponse;
import com.devmohamedibrahim1997.populartest.model.DetailsResponse;
import com.devmohamedibrahim1997.populartest.model.MovieResponse;
import com.devmohamedibrahim1997.populartest.model.MovieVideos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBAPI {

    //movies
    //"https://api.themoviedb.org/3/movie/popular?api_key=d032214048c9ca94d788dcf68434f385&page=1"
    @GET("/3/movie/{category}?api_key=d032214048c9ca94d788dcf68434f385")
    Call<MovieResponse> getMovieResponse(@Path("category") String category,@Query("page") int page);


    //search
    //"https://api.themoviedb.org/3/search/movie?api_key=d032214048c9ca94d788dcf68434f385&query=lion"
    @GET("/3/search/movie?api_key=d032214048c9ca94d788dcf68434f385")
    Call<MovieResponse> getSearchResponse(@Query("query") String query, @Query("page") int page);

    //movie detail
    //https://api.themoviedb.org/3/movie/474350?api_key=d032214048c9ca94d788dcf68434f385
    @GET("/3/movie/{movie_id}?api_key=d032214048c9ca94d788dcf68434f385")
    Call<DetailsResponse> getMovieDetails(@Path("movie_id") Integer movieId);


    //movie credit
    //https://api.themoviedb.org/3/movie/384018/credits?api_key=d032214048c9ca94d788dcf68434f385
    @GET("/3/movie/{movie_id}/credits?api_key=d032214048c9ca94d788dcf68434f385")
    Call<CreditResponse> getMovieCreditsResponse(@Path("movie_id") Integer movieId);


    //recommendations
    //https://api.themoviedb.org/3/movie/384018/recommendations?api_key=d032214048c9ca94d788dcf68434f385
    @GET("/3/movie/{movie_id}/recommendations?api_key=d032214048c9ca94d788dcf68434f385")
    Call<MovieResponse> getRecommendationResponse(@Path("movie_id") Integer movieId);


    //similar
    //https://api.themoviedb.org/3/movie/384018/similar?api_key=d032214048c9ca94d788dcf68434f385
    @GET("/3/movie/{movie_id}/similar?api_key=d032214048c9ca94d788dcf68434f385")
    Call<MovieResponse> getSimilarResponse(@Path("movie_id") Integer movieId);


    //movie videos
    //https://api.themoviedb.org/3/movie/384018/videos?api_key=d032214048c9ca94d788dcf68434f385
    @GET("/3/movie/{movie_id}/videos?api_key=d032214048c9ca94d788dcf68434f385")
    Call<MovieVideos> getMovieVideosResponse(@Path("movie_id") Integer movieId);

}
