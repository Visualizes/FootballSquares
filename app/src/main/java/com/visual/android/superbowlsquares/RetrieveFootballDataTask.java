package com.visual.android.superbowlsquares;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Created by Rami on 12/16/2015.
 */
public abstract class RetrieveFootballDataTask extends AsyncTask<Void, Void, String> {

    public static int SLEEP_TIME = 2000;

    protected String doInBackground(Void... params) {
        StringBuilder sb = new StringBuilder();
        try {
            URL cbssports = new URL("http://www.cbssports.com/nfl/scoreboard?nocache=" + new Date().getTime());
            URL espn = new URL("http://www.espn.com/nfl/scoreboard?nocache=" + new Date().getTime());
            URL foxsports = new URL("http://www.foxsports.com/nfl/scores?nocache=" + new Date().getTime());
            URL msn = new URL("https://www.msn.com/en-us/sports/nfl/scores?nocache=" + new Date().getTime());
            URL usatoday = new URL("http://www.usatoday.com/sports/nfl/scores?nocache=" + new Date().getTime());
            URL nbc = new URL("http://scores.nbcsports.com/fb/scoreboard.asp?nocache=" + new Date().getTime());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            espn.openStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.d("TeamSelection", "AsyncTask done");

        try{
            Log.d("Sleep", "successful");
            Thread.sleep(SLEEP_TIME);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        String webSourceCode = sb.toString();
/*
        int index = -1;
        int index2 = -1;

        //checks if webSourceCode exists, rarely a FileNotFound exception will occur
        if (!webSourceCode.equals("")){
            //displays all live games
            index = webSourceCode.indexOf("<th colspan=\"2\" class=\"downlevelOnly\">Live</th>");
            index2 = webSourceCode.indexOf("<div class=\"section nolivesection\"");
        }

        if (index != -1 && index2 != -1) {
            webSourceCode = webSourceCode.substring(index, index2);
        }
*/
        return webSourceCode;
    }


}//AsyncTask end