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
    @GET("/3/movie/{category}?api_key=ce9463086a1c2dfa01c0744e43363861")
    Call<MovieResponse> getMovieResponse(@Path("category") String category,@Query("page") int page);


    //search
    @GET("/3/search/movie?api_key=ce9463086a1c2dfa01c0744e43363861")
    Call<MovieResponse> getSearchResponse(@Query("query") String query, @Query("page") int page);

    //movie detail
    @GET("/3/movie/{movie_id}?api_key=ce9463086a1c2dfa01c0744e43363861")
    Call<DetailsResponse> getMovieDetails(@Path("movie_id") Integer movieId);


    //movie credit
    @GET("/3/movie/{movie_id}/credits?api_key=ce9463086a1c2dfa01c0744e43363861")
    Call<CreditResponse> getMovieCreditsResponse(@Path("movie_id") Integer movieId);


    //recommendations
    @GET("/3/movie/{movie_id}/recommendations?api_key=ce9463086a1c2dfa01c0744e43363861")
    Call<MovieResponse> getRecommendationResponse(@Path("movie_id") Integer movieId);


    //similar
    @GET("/3/movie/{movie_id}/similar?api_key=ce9463086a1c2dfa01c0744e43363861")
    Call<MovieResponse> getSimilarResponse(@Path("movie_id") Integer movieId);


    //movie videos
    @GET("/3/movie/{movie_id}/videos?api_key=ce9463086a1c2dfa01c0744e43363861")
    Call<MovieVideos> getMovieVideosResponse(@Path("movie_id") Integer movieId);

}
