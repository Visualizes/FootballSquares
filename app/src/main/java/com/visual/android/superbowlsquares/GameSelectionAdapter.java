package com.visual.android.superbowlsquares;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rami on 10/9/2015.
 */
public class GameSelectionAdapter extends ArrayAdapter<Game> {

    private List<Game> items;
    private UserChoices userChoices;

    public GameSelectionAdapter(Context context, List<Game> items, UserChoices userChoices) {
        super(context, 0, items);
        this.items = items;
        this.userChoices = userChoices;
        System.out.println("NEW GAME SELECTION ADAPTER");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_gametext, parent, false);
        }

        final TextView gameName = (TextView)convertView.findViewById(R.id.nameItem);
        if (items.get(position) != null) {
            gameName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Game selectedGame = items.get(position);
                    userChoices.setGame(selectedGame);

                    Intent i = new Intent(getContext(), MainBoardActivity.class);
                    i.putExtra("UserChoices", userChoices);
                    getContext().startActivity(i);
                }
            });
        }
        gameName.setText(items.get(position).getNFLHomeTeamName() + " vs. " + items.get(position).getNFLAwayTeamName());
        TextView date = (TextView)convertView.findViewById(R.id.date);
        date.setText(items.get(position).getDate());

        if (date.getText().toString().equals("LIVE")) {
            date.setTypeface(null, Typeface.BOLD);
        }
        else {
            date.setTypeface(null, Typeface.NORMAL);
        }
        return convertView;
    }
}
