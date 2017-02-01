package com.visual.android.superbowlsquares;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Collections;

/**
 * Created by Rami on 1/10/2016.
 */
public class MainBoardController {

    private TableRow winningRow;
    private TextView[][] board;
    private TableLayout layout;
    private TextView[] Row_One_Column = new TextView[10];
    private TextView[] Column_One_Row = new TextView[10];
    private TableRow[] rows;
    public static boolean settingUp = false;
    private UserChoices userChoices;

    public MainBoardController(TableLayout layout, TextView[][] board, TableRow[] rows, UserChoices userChoices){

        this.userChoices = userChoices;
        this.board = board;
        this.layout = layout;
        this.rows = rows;

        for (int i = 0; i < 11; i++){
            rows[i] = (TableRow)layout.getChildAt(i);
        }

        setUpBoard();

        if (userChoices.getRow().size() != 10 && userChoices.getColumn().size() != 10) {
            settingUp = true;
            animateAndSetRowCol();
        }
        else {
            setRowCol();
        }

    }

    private void setUpBoard(){
        //sets the ints of the arrays equal to each respective child
        for (int i = 0; i < 10; i++){
            Row_One_Column[i] = (TextView)rows[0].getChildAt(i + 1);
            Column_One_Row[i] = (TextView)rows[i+1].getChildAt(0);
            for (int y = 0; y < 10; y++) {
                int position = (i*10) + y;
                board[i][y] = (TextView) rows[i+1].getChildAt(y+1);
                board[i][y].setText(userChoices.getNamesOnBoard().get(position));
            }
        }
    }

    public void clearOldWinner(int oldHomeTeamScore, int oldAwayTeamScore){
        winningRow = (TableRow)layout.getChildAt(1 + userChoices.getColumn().indexOf(oldAwayTeamScore));
        winningRow.getChildAt(1 + userChoices.getRow().indexOf(oldHomeTeamScore)).setBackgroundColor(Color.WHITE);
    }

    public void clearAll(){
        for (int i = 0; i < 10; i++) {
            for (int y = 0; y < 10; y++) {
                board[i][y].setBackgroundColor(Color.WHITE);
            }
        }
    }

    public void showWinner(int newHomeTeamScore, int newAwayTeamScore){
        //I added 1 because the Button isn't apart of the array
        winningRow = (TableRow)layout.getChildAt(1 + userChoices.getColumn().indexOf(newAwayTeamScore));
        winningRow.getChildAt(1 + userChoices.getRow().indexOf(newHomeTeamScore)).setBackgroundColor(Color.YELLOW);
    }

    private void shuffleRowColNumbers(){

        if (userChoices.getRow().size() != 10 && userChoices.getColumn().size() != 10) {
            for (int a = 0; a < 10; a++) {
                userChoices.getRow().add(a);
                userChoices.getColumn().add(a);
            }
        }

        //Random number generator for row
        Collections.shuffle(userChoices.getRow());
        //Random number generator for column
        Collections.shuffle(userChoices.getColumn());

    }

    public void animateAndSetRowCol(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(settingUp) {
                    shuffleRowColNumbers();
                    setRowCol();
                    animateAndSetRowCol();
                }
            }
        }, 50);
    }

    public void setRowCol(){
        for (int i = 0; i < 10; i++){
            Row_One_Column[i].setText(String.valueOf(userChoices.getRow().get(i)));
            Column_One_Row[i].setText(String.valueOf(userChoices.getColumn().get(i)));
            setTeamColors(userChoices.getGame().getNFLHomeTeamName(), userChoices.getGame().getNFLAwayTeamName());
        }
    }

    public void changeAnimationState(boolean state){
        settingUp = state;
    }

    public void clearBoard(){
        userChoices.getNamesOnBoard().clear();
        userChoices.getRow().clear();
        userChoices.getColumn().clear();
        settingUp = true;
        animateAndSetRowCol();

        for (int i = 0; i < 10; i++) {
            for (int y = 0; y < 10; y++) {
                board[i][y].setText("");
                board[i][y].setBackgroundColor(Color.WHITE);
            }
        }
    }

    public void setTeamColors(String homeTeam, String awayTeam){
        String[] teamNames = GameInformation.getTeamNames();
        int[] teamColors = GameInformation.getTeamColors();

        for (int i = 0; i < teamNames.length; i++){
            if (homeTeam.equals(teamNames[i])){
                for (int u = 0; u < 10; u++){
                    if (i > 21) {
                        if (u%3 == 0) {
                            Row_One_Column[u].setTextColor(teamColors[(2 * i) + (i-20)]);
                        }
                        else if(u%2 == 0){
                            Row_One_Column[u].setTextColor(teamColors[(2 * i) + (i-21)]);
                        }
                        else{
                            Row_One_Column[u].setTextColor(teamColors[(2 * i) + (i-22)]);
                        }
                    }
                    else{
                        if (u%2 == 0) {
                            Row_One_Column[u].setTextColor(teamColors[(2 * i) + 1]);
                        }
                        else{
                            Row_One_Column[u].setTextColor(teamColors[2 * i]);
                        }
                    }
                }
            }
            if (awayTeam.equals(teamNames[i])){
                for (int u = 0; u < 10; u++){
                    if (i > 21) {
                        if (u%3 == 0) {
                            Column_One_Row[u].setTextColor(teamColors[(2 * i) + (i-20)]);
                        }
                        else if(u%2 == 0){
                            Column_One_Row[u].setTextColor(teamColors[(2 * i) + (i-21)]);
                        }
                        else{
                            Column_One_Row[u].setTextColor(teamColors[(2 * i) + (i-22)]);
                        }
                    }
                    else{
                        if (u%2 == 0) {
                            Column_One_Row[u].setTextColor(teamColors[(2 * i) + 1]);
                        }
                        else{
                            Column_One_Row[u].setTextColor(teamColors[(2 * i)]);
                        }
                    }
                }
            }
        }

    }

}
