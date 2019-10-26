
package com.devmohamedibrahim1997.populartest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    @SerializedName("results")
    @Expose
    private List<Movie> movies;

    public MovieResponse(Integer totalPages, List<Movie> movies) {
        this.totalPages = totalPages;
        this.movies = movies;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<Movie> getMovies() {
        return movies;
    }

}
