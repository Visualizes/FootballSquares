package com.visual.android.superbowlsquares;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

/**
 * Created by RamiK on 5/17/2016.
 */
public class NavigateOnMenuItemSelected {

    public NavigateOnMenuItemSelected(MenuItem item, final Context context, final UserChoices userChoices, int currentId){

        int id = item.getItemId();
        //if currentId = id, it would be an infinite intent to the same activity
        if (currentId != id) {
            if (id == R.id.nav_set_up) {
                Intent i = new Intent(context, BoardSetUpActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            } else if (id == R.id.nav_game) {
                Intent i = new Intent(context, GameSelectionActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            } else if (id == R.id.nav_main) {
                Intent i = new Intent(context, MainBoardActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            } else if (id == R.id.nav_home) {
                Intent i = new Intent(context, StartingScreenActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            } else if (id == R.id.nav_save) {
                Intent i = new Intent(context, PreviewGameActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            } else if (id == R.id.nav_add_people) {
                Intent i = new Intent(context, NameInputActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            } else if (id == R.id.nav_load) {
                Intent i = new Intent(context, LoadGameActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            } else if (id == R.id.nav_share) {
                Intent i = new Intent(context, ShareGameActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            } else if (id == R.id.nav_join) {
                Intent i = new Intent(context, JoinGameActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            } else if (id == R.id.nav_feedback_report) {
                Intent i = new Intent(context, FeedbackErrorActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            } else if (id == R.id.nav_about) {
                Intent i = new Intent(context, AboutActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            } else if (id == R.id.nav_help) {
                Intent i = new Intent(context, HelpActivity.class);
                i.putExtra("UserChoices", userChoices);
                context.startActivity(i);
            }
        }
    }
}
