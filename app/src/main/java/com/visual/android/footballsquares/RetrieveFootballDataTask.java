package com.visual.android.footballsquares;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;

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

    protected String doInBackground(Void... params) {
        StringBuilder sb = new StringBuilder();
        try {
            URL nfl = new URL("http://www.cbssports.com/nfl/scoreboard?nocache=" + new Date().getTime());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            nfl.openStream()));
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
            Thread.sleep(5000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        String webSourceCode = sb.toString();
        sb = null;

        return webSourceCode;
    }


}//AsyncTask end