
package com.devmohamedibrahim1997.populartest.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieVideos {

    @SerializedName("results")
    @Expose
    private List<Videos> videos;

    public MovieVideos(List<Videos> videos) {
        this.videos = videos;
    }

    public List<Videos> getVideos() {
        return videos;
    }

}
