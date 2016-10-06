package com.visual.android.superbowlsquares;

import java.io.Serializable;

/**
 * Created by Rami on 12/14/2015.
 */
public class Game implements Serializable {

    public String homeName;
    public String awayName;
    public int homeScore;
    public int awayScore;
    public String date;

    public Game(String homeName, String awayName, int homeScore, int awayScore, String date){
        this.homeName = homeName;
        this.awayName = awayName;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.date = date;
    }

    public String getNFLHomeTeamName(){
        return homeName;
    }
    public String getNFLAwayTeamName(){
        return awayName;
    }
    public int getHomeTeamScore(){
        return homeScore;
    }
    public int getAwayTeamScore(){
        return awayScore;
    }
    public String getDate() {
        return date;
    }

}
