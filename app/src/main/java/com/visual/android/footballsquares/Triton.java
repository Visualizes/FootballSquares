package com.visual.android.footballsquares;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by RamiK on 7/20/2016.
 */

public class Triton{

    private static Callback callback;
    private static String function;
    private static int responseCode;

    public Triton(String function){
        Triton.function = function;
    }

    public void execute(){
        new async().execute();
    }

    public void execute(String... args){
        new async().execute(args);
    }

    public void execute(Callback callback){
        Triton.callback = callback;
        new async().execute();
    }

    public void execute(Callback callback, String... args){
        Triton.callback = callback;
        new async().execute(args);
    }

    private static class async extends AsyncTask<String, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = new JSONObject();
            StringBuffer response = new StringBuffer();
            try {
                URL tritonURL = new URL("http://triton.cloud:8081/" + function);
                HttpURLConnection con = (HttpURLConnection) tritonURL.openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Triton-App", "08650888-f86f-4ea6-b64b-00788c4f125a");

                con.getOutputStream().write(("args=" + params[0]).getBytes());
                responseCode = con.getResponseCode();
                System.out.println("\nSending request to URL : " + tritonURL);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(response.toString());

            try {
                jsonObject.put("responseCode", responseCode);
                jsonObject = new JSONArray(response.toString()).getJSONObject(0);
                System.out.println(jsonObject);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject array) {
            callback.call(array);
        }
    }

}


