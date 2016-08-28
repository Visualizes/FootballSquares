package com.visual.android.footballsquares;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.List;

/**
 * Created by RamiK on 7/26/2016.
 */
public class ShareGameActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserChoices userChoices;
    private byte bytes[];
    NavigationViewController navigationViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharegame);

        Bundle extras = getIntent().getExtras();
        userChoices = (UserChoices) extras.get("UserChoices");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Share Game");
        toolbar.setSubtitle("Football Squares");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_share);
        navigationViewController = new NavigationViewController(navigationView, userChoices, R.id.nav_share);

        SecureRandom random = new SecureRandom();
        bytes = new byte[20];
        random.nextBytes(bytes);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UUID", bytes.toString().substring(3));
            jsonObject.put("HomeTeamName", userChoices.getGame().getNFLHomeTeamName());
            jsonObject.put("AwayTeamName", userChoices.getGame().getNFLAwayTeamName());
            jsonObject.put("HomeTeamScore", userChoices.getGame().getHomeTeamScore());
            jsonObject.put("AwayTeamScore", userChoices.getGame().getAwayTeamScore());
            jsonObject.put("DateOfGame", userChoices.getGame().getDate());
            jsonObject.put("Names", strArrayToJSON(userChoices.getArrayOfNames()));
            jsonObject.put("NamesOnBoard", strArrayToJSON(userChoices.getNamesOnBoard()));
            jsonObject.put("Row", intArrayToJSON(userChoices.getRow()));
            jsonObject.put("Col", intArrayToJSON(userChoices.getColumn()));
            //jsonObject.put("Screenshot", userChoices.getScreenshot());
            new Triton("put").execute(new Callback() {
                @Override
                public void call(JSONObject array) {
                    try {
                        if (array.getInt("responseCode") == 200) {
                            TextView code = (TextView) findViewById(R.id.code);
                            code.setText(bytes.toString().substring(3));
                            code.setTypeface(null, Typeface.BOLD);
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, jsonObject.toString());

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private JSONArray strArrayToJSON(List<String> array){
        JSONArray jsonArray = new JSONArray();
        for (String element : array){
            jsonArray.put(element);
        }
        return jsonArray;
    }

    private JSONArray intArrayToJSON(List<Integer> array){
        JSONArray jsonArray = new JSONArray();
        for (int element : array){
            jsonArray.put(element);
        }
        return jsonArray;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            navigationViewController.updateItemSelected();

        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        new NavigateOnMenuItemSelected(item, ShareGameActivity.this, userChoices, R.id.nav_share);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
