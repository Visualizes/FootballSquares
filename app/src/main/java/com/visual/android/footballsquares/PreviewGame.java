package com.visual.android.footballsquares;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
