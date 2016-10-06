package com.visual.android.superbowlsquares;

import java.io.Serializable;

/**
 * Created by RamiK on 9/14/2016.
 */
public class FragmentConfig implements Serializable {

    private int layout;
    private String message;
    private String id;

    public FragmentConfig(int layout, String message, String id){
        this.id = id;
        this.layout = layout;
        this.message = message;

    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getLayout() {
        return layout;
    }
}
