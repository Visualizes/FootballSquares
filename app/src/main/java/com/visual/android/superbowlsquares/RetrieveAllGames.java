package com.visual.android.superbowlsquares;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rami on 12/14/2015.
 */
public class RetrieveAllGames implements Serializable {

    List<Game> games;

    public RetrieveAllGames(String webSourceCode){

        games = new ArrayList<>();

        if (webSourceCode != null) {

            String teamNameKeyword;
            String scoreKeyWord;
            String dateKeyWord;

            String teamNameKeyword2;
            String scoreKeyWord2;
            String dateKeyWord2;

            //CBSSPORTS
            /*
            teamNameKeyword = "class=\"logo\"><a href=\"http://www.cbssports.com/nfl/teams/page/";
            scoreKeyWord = "<td class=\"total-score\">";
            dateKeyWord = "mmA\"}'>                                ";

            teamNameKeyword2 = "<";
            scoreKeyWord2 = "<";
            dateKeyWord2 = "<";


            //USATODAY
            teamNameKeyword = "<li class=\"teamlinks\"><a href=\"/sports/nfl/";
            teamNameKeyword2 = "/statistics/\" data-ht=\"";
            scoreKeyWord = "<li class=\"outcomes total\">";
            scoreKeyWord2 = "<";
            dateKeyWord = "<hgroup><h3>                    ";
            dateKeyWord2 = " ";

            //MSN
            teamNameKeyword = " alignright size1\">";
            teamNameKeyword2 = "<";
            scoreKeyWord = "<td class=\"totalscore teamlineup ";
            scoreKeyWord2 = "<";
            dateKeyWord = "<td class=\"matchdate size1234\" rowspan=\"2\">";
            dateKeyWord2 = "<";

            //NBCSports
            teamNameKeyword = "></span></span><a href=\"/fb/teamstats.asp?team=";
            teamNameKeyword2 = "<";


            //Foxsports
            teamNameKeyword = "\"http://www.foxsports.com/nfl/story/week-";
            teamNameKeyword2 = "?";
            scoreKeyWord = "\"score\":\"";
            scoreKeyWord2 = "\"";
            dateKeyWord = "\"infoB\":\"";
            dateKeyWord2 = "\"";

            */

            //ESPN
            teamNameKeyword = "\"shortDisplayName\":\"";
            teamNameKeyword2 = "\"";
            scoreKeyWord = "}],\"score\":\"";
            scoreKeyWord2 = "\"";
            //long date
            //dateKeyWord = "EDT\",\"detail\":\"";
            //short date
            dateKeyWord = "\",\"shortDetail\":\"";
            dateKeyWord2 = "\"";

            List<String> arrayOfScoresStrings = new ArrayList<>();
            List<String> arrayOfTeamNames = new ArrayList<>();
            List<String> arrayOfDates = new ArrayList<>();

            //gets team names
            parseData(webSourceCode, teamNameKeyword, teamNameKeyword2, arrayOfTeamNames, "TEAM_NAMES");
            //gets team scores
            parseData(webSourceCode, scoreKeyWord, scoreKeyWord2, arrayOfScoresStrings, "SCORES");
            //gets date of games
            parseData(webSourceCode, dateKeyWord, dateKeyWord2, arrayOfDates, "DATES");

            //converts string to integers
            List<Integer> arrayOfScores = new ArrayList<>();
            for (String s : arrayOfScoresStrings) {
                try {
                    arrayOfScores.add(Integer.valueOf(s));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            //fills beginning of array with "-"
            while (arrayOfTeamNames.size()/2 > arrayOfDates.size()) arrayOfTeamNames.remove(arrayOfTeamNames.size() - 1);
            //adds zeros if no score exists yet
            while (arrayOfTeamNames.size() > arrayOfScores.size()) arrayOfScores.add(0);

            for (int i = 0; i < arrayOfScores.size() / 2; i++) {/*
                Log.d("home team: ", arrayOfTeamNames.get(2 * i));
                Log.d("away team: ", arrayOfTeamNames.get((2 * i) + 1));
                Log.d("home score: ", String.valueOf(arrayOfScores.get(2 * i)));
                Log.d("away score: ", String.valueOf(arrayOfScores.get((2 * i) + 1)));
                Log.d("date: ", arrayOfDates.get(i));*/
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

    private void parseData(String webSourceCode, String keyWord, String keyWord2, List<String> array, String key){
        Pattern p = Pattern.compile(Pattern.quote(keyWord) + "(.*?)" + Pattern.quote(keyWord2));
        Matcher m = p.matcher(webSourceCode);
        int i = 0;
        while (m.find()) {
            switch (key){
                /*
                case "SCORES":
                    //MSN
                    array.add(cleanString(m.group(1).replaceAll("\\s+","")));
                    System.out.println((cleanString(m.group(1).replaceAll("\\s+",""))));

                case "TEAM_NAMES":
                    //FOXSports
                    array.add(getFirstTeamName(m.group(1)));
                    array.add(getSecondTeamName(m.group(1)));
                    break;
                */
                case "DATES":
                    if (i%2 == 0){
                        array.add(m.group(1));
                    }
                    i++;
                    break;
                default:
                    array.add(m.group(1));
            }
        }
        System.out.println(array);
    }

    private String getFirstTeamName(String str){
        str = str.substring(5);
        int index = str.indexOf("-");
        str = str.substring(index + 1);
        index = str.indexOf("-");
        System.out.println(str + "xx");
        return str.substring(0,1).toUpperCase() + str.substring(1, index);

    }

    private String getSecondTeamName(String str){
        for (int i = 0; i < 4; i++){
            int index = str.indexOf("-");
            str = str.substring(index + 1);
        }

        int index = str.indexOf("-");
        System.out.println(str);
        return str.substring(0,1).toUpperCase() + str.substring(1, index);

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
