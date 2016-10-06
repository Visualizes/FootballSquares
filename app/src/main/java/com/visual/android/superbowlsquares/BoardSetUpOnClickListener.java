package com.visual.android.superbowlsquares;


import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rami on 12/24/2015.
 */
public class BoardSetUpOnClickListener implements View.OnClickListener {

    public static boolean isFrozen = false;
    public static int nameIndex = 0;
    public static boolean deleteMode = false;
    private ActionBar actionBar;
    private int fi;
    private int fy;
    private UserChoices userChoices;

    public BoardSetUpOnClickListener(int fy, int fi, ActionBar actionBar, UserChoices userChoices){
        this.userChoices = userChoices;
        this.actionBar = actionBar;
        this.fy = fy;
        this.fi = fi;
    }

    @Override
    public void onClick(View v){

        int position = (fi * 10) + fy;

        List<String> arrayOfNames = userChoices.getArrayOfNames();

        TextView t = (TextView) v;
        if (!deleteMode) {
            t.setText(arrayOfNames.get(nameIndex));

            if (!isFrozen) {
                nameIndex++;
                if (nameIndex == arrayOfNames.size()) {
                    nameIndex = 0;
                }
            }

            actionBar.setTitle("It's " + arrayOfNames.get(nameIndex) + "'s turn.");
            userChoices.getNamesOnBoard().set(position, t.getText().toString());
        }
        else{
            t.setText("");
            userChoices.getNamesOnBoard().set(position, t.getText().toString());
        }
    }


}
