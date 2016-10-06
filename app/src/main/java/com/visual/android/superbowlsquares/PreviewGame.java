package com.visual.android.superbowlsquares;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

/**
 * Created by RamiK on 8/4/2016.
 */
public class PreviewGame {

    private Context context;
    private UserChoices userChoices;

    public PreviewGame(View view, Context context, UserChoices userChoices){
        this.context = context;
        this.userChoices = userChoices;
    }

    public ArrayAdapter<String> getArrayAdapter(int layout){
        return new ArrayAdapter<>(
                context,
                layout,
                userChoices.getArrayOfNames() );
    }

    public String getGameText(){
        return userChoices.getGame().getNFLHomeTeamName() + " vs. "+ userChoices.getGame().getNFLAwayTeamName();
    }

}
