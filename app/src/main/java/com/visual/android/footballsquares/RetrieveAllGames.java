package com.visual.android.footballsquares;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rami on 12/14/2015.
 */
public class RetrieveAllGames implements Serializable {

    List<Game> games;

    public RetrieveAllGames(String webSourceCode){

        if (webSourceCode != null) {

            String teamNameKeyword = "class=\"teamLogo\"></a><div class=\"teamLocation\"><a href=\"/nfl/teams/page/";
            String scoreKeyWord = "<td class=\"finalScore\">";
            String dateKeyWord = "data-gmt-format=\"%r  %q %e";

            games = new ArrayList<>();
            List<String> arrayOfScoresStrings = new ArrayList<>();
            List<String> arrayOfTeamNames = new ArrayList<>();
            List<String> arrayOfDates = new ArrayList<>();

            //gets team names
            parseData(webSourceCode, teamNameKeyword, arrayOfTeamNames, "TEAM_NAMES");
            //gets team scores
            parseData(webSourceCode, scoreKeyWord, arrayOfScoresStrings, "SCORES");
            //gets date of games
            parseData(webSourceCode, dateKeyWord, arrayOfDates, "DATES");

            //converts string to integers
            List<Integer> arrayOfScores = new ArrayList<>();
            for (String s : arrayOfScoresStrings) {
                try {
                    arrayOfScores.add(Integer.valueOf(s));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            //adds zeros if no score exists yet
            while (arrayOfTeamNames.size() > arrayOfScores.size()) arrayOfScores.add(0);
            //fills beginning of array with "LIVE"
            while (arrayOfTeamNames.size()/2 > arrayOfDates.size()) arrayOfDates.add(0, "LIVE");

            for (int i = 0; i < arrayOfScores.size() / 2; i++) {
                Log.d("home team: ", arrayOfTeamNames.get(2 * i));
                Log.d("away team: ", arrayOfTeamNames.get((2 * i) + 1));
                Log.d("home score: ", String.valueOf(arrayOfScores.get(2 * i)));
                Log.d("away score: ", String.valueOf(arrayOfScores.get((2 * i) + 1)));
                Log.d("date: ", arrayOfDates.get(i));
                games.add(new Game(
                        arrayOfTeamNames.get(2 * i), arrayOfTeamNames.get((2 * i) + 1),
                        arrayOfScores.get(2 * i), arrayOfScores.get((2 * i) + 1),
                        arrayOfDates.get(i)));
            }

        }

        else{
            Log.d("ERROR", "webSourceCode null");
        }
    }//end constructor

    private void parseData(String webSourceCode, String keyWord, List<String> array, String key){
        Pattern p = Pattern.compile(Pattern.quote(keyWord) + "(.*?)" + Pattern.quote("<"));
        Matcher m = p.matcher(webSourceCode);
        while (m.find()) {
            switch (key){
                case "TEAM_NAMES":
                    array.add(String.valueOf(GameInformation.getTeamCityName().get(cleanString(m.group(1)))));
                    break;
                case "DATES":
                    array.add(cleanString(m.group(1)));
                    break;
                default:
                    array.add(m.group(1));
            }
        }
    }

    private String cleanString(String str){
        while (str.contains(">")){
            str = str.substring(1);
        }
        return str;
    }

    public List<Game> getGames(){
        return games;
    }

}//end class
