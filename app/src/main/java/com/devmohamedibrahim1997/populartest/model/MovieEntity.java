package com.devmohamedibrahim1997.populartest.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "watchLater_table")
public class MovieEntity {

    @PrimaryKey(autoGenerate = true)
    public Integer mId;

    private Integer id;
    private Double voteAverage;
    private String title;
    private String posterPath;
    private String overView;
    private String releaseDate;

    public MovieEntity(Integer id, Double voteAverage, String title, String posterPath, String overView, String releaseDate) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.overView = overView;
        this.releaseDate = releaseDate;
    }

    public Integer getId() {
        return id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverView() {
        return overView;
    }


}
