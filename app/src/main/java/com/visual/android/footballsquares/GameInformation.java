package com.visual.android.footballsquares;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rami on 2/16/2016.
 */
public class GameInformation {
    public static String webSourceCode;
    public static int recursion = 0;
    public static RecursiveRetrieveScoreTask recursiveRetrieveScoreTask;
    public static RetrieveFootballDataTask retrieveFootballDataTask;

    private static Map<String, String> teamCityName;
    private static int[] teamColors = {
            Color.parseColor("#000000"), Color.parseColor("#A71930"), //Atlanta Falcons (0,1)
            Color.parseColor("#9E7C0C"), Color.parseColor("#241773"), //Baltimore Ravens (2,3)
            Color.parseColor("#C60C30"), Color.parseColor("#00338D"), //Buffalo Bills (4,5)
            Color.parseColor("#DD4814"), Color.parseColor("#03202F"), //Chicago Bears (6,7)
            Color.parseColor("#FB4F14"), Color.parseColor("#000000"), //Cincinnati Bengals (8,9)
            Color.parseColor("#4C230E"), Color.parseColor("#FB4F14"), //Cleveland Browns (10,11)
            Color.parseColor("#FFFFFF"), Color.parseColor("#002E4D"), //Dallas Cowboys (12,13)
            Color.parseColor("#FB4F14"), Color.parseColor("#002244"), //Denver Broncos (14,15)
            Color.parseColor("#FFB612"), Color.parseColor("#294239"), //Green Bay Packers (16,17)
            Color.parseColor("#B31B34"), Color.parseColor("#02253A"), //Houston Texans (18,19)
            Color.parseColor("#FFFFFF"), Color.parseColor("#002C5F"), //Indianapolis Colts (20,21)
            Color.parseColor("#FFFFFF"), Color.parseColor("#E31837"), //Kansas City Chiefs (22,23)
            Color.parseColor("#FFC62F"), Color.parseColor("#4F2683"), //Minnesota Vikings (24,25)
            Color.parseColor("#000000"), Color.parseColor("#9F8958"), //New Orleans Saints (26,27)
            Color.parseColor("#FFFFFF"), Color.parseColor("#203731"), //New York Jets (28,29)
            Color.parseColor("#A5ACAF"), Color.parseColor("#000000"), //Oakland Raiders (30,31)
            Color.parseColor("#000000"), Color.parseColor("#FFBF00"), //Pittsburgh Steelers (32,33)
            Color.parseColor("#B3995D"), Color.parseColor("#AA0000"), //San Francisco 49ers (34,35)
            Color.parseColor("#030F1F"), Color.parseColor("#283E67"), //Seattle Seahawks (36,37)
            Color.parseColor("#00295B"), Color.parseColor("#C1A05B"), //St. Louis Rams (38,39)
            Color.parseColor("#665C50"), Color.parseColor("#D50A0A"), //Tampa Bay Buccaneers (40,41)
            Color.parseColor("#8C001A"), Color.parseColor("#FFBF00"), //Washington Redskins (42,43)

            Color.parseColor("#A5ACAF"), Color.parseColor("#000000"), Color.parseColor("#004953"), //Philadelphia Eagles (44,45,46)
            Color.parseColor("#A71930"), Color.parseColor("#A5ACAF"), Color.parseColor("#0B2265"), //New York Giants (48,49,50)
            Color.parseColor("#F58220"), Color.parseColor("#005778"), Color.parseColor("#008E97"), //Miami Dolphins (51,52,53)
            Color.parseColor("#000000"), Color.parseColor("#FFB612"), Color.parseColor("#97233F"), //Arizona Cardinals (54,55,56)
            Color.parseColor("#0097C6"), Color.parseColor("#101B24"), Color.parseColor("#A2A7AB"), //Carolina Panthers (57,68,69)
            Color.parseColor("#B0B7BC"), Color.parseColor("#006EA1"), Color.parseColor("#000000"), //Detroit Lions (60,61,62)
            Color.parseColor("#00839C"), Color.parseColor("#101B24"), Color.parseColor("#FFFFFF"), //Jacksonville Jaguars (63,64,65)
            Color.parseColor("#C60C30"), Color.parseColor("#B0B7BC"), Color.parseColor("#002244"), //New England Patriots (66,67,68)
            Color.parseColor("#05173C"), Color.parseColor("#0F83B8"), Color.parseColor("#FFBF00"), //San Diego Chargers (69,70,71)
            Color.parseColor("#4B92DB"), Color.parseColor("#C60C30"), Color.parseColor("#002244") //Tennessee Titans (72,73,74)
    };

    //                        0          1         2        3         4         5          6          7          8         9        10        11        12        13        14        15
    private static String[] teamNames = { "Falcons", "Ravens", "Bills", "Bears", "Bengals", "Browns", "Cowboys", "Broncos", "Packers", "Texans", "Colts", "Chiefs", "Vikings", "Saints", "Jets", "Raiders",
            "Steelers", "49ers", "Seahawks", "Rams", "Buccaneers", "Redskins", "Eagles", "Giants", "Dolphins", "Cardinals", "Panthers", "Lions", "Jaguars", "Patriots", "Chargers", "Titans"
            //          16         17        18         19        20           21         22        23         24          25          26         27        28         29          30          31
    };

    public GameInformation(){
        teamCityName = new HashMap<>();
        teamCityName.put("Denver", "Broncos");
        teamCityName.put("Dallas", "Cowboys");
        teamCityName.put("New England", "Patriots");
        teamCityName.put("Pittsburgh", "Steelers");
        teamCityName.put("Seattle", "Seahawks");
        teamCityName.put("Oakland", "Raiders");
        teamCityName.put("Philadelphia", "Eagles");
        teamCityName.put("Green Bay", "Packers");
        teamCityName.put("San Francisco", "49ers");
        teamCityName.put("NY Giants", "Giants");
        teamCityName.put("Minnesota", "Vikings");
        teamCityName.put("Chicago", "Bears");
        teamCityName.put("Washington", "Redskins");
        teamCityName.put("Carolina", "Panthers");
        teamCityName.put("New Orleans", "Saints");
        teamCityName.put("NY Jets", "Jets");
        teamCityName.put("Los Angeles", "Rams");
        teamCityName.put("Baltimore", "Ravens");
        teamCityName.put("San Diego", "Chargers");
        teamCityName.put("Indianapolis", "Colts");
        teamCityName.put("Arizona", "Cardinals");
        teamCityName.put("Houston", "Texans");
        teamCityName.put("Cleveland", "Browns");
        teamCityName.put("Detroit", "Lions");
        teamCityName.put("Jacksonville", "Jaguars");
        teamCityName.put("Buffalo", "Bills");
        teamCityName.put("Cincinnati", "Bengals");
        teamCityName.put("Atlanta", "Falcons");
        teamCityName.put("Tampa Bay", "Buccaneers");
        teamCityName.put("Kansas City", "Chiefs");
        teamCityName.put("Tennessee", "Titans");
        teamCityName.put("Miami", "Dolphins");


    }

    public static Map<String, String> getTeamCityName() {
        return teamCityName;
    }

    public static int[] getTeamColors() {
        return teamColors;
    }

    public static String[] getTeamNames() {
        return teamNames;
    }
}
