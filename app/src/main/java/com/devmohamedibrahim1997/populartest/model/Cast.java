
package com.devmohamedibrahim1997.populartest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cast {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("character")
    @Expose
    private String character;

    @SerializedName("profile_path")
    @Expose
    private Object profilePath;

    public Cast(String name, String character, Object profilePath) {
        this.name = name;
        this.character = character;
        this.profilePath = profilePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getProfilePath() {
        return profilePath;
    }

    public String getCharacter() {
        return character;
    }


}
