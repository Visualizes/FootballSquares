package com.visual.android.footballsquares;

/**
 * Created by RamiK on 8/27/2016.
 */
public class LoadGame {

    private String name;
    private String description;

    public LoadGame (String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
