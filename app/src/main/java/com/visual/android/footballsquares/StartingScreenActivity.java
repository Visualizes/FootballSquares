package com.visual.android.footballsquares;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

public class StartingScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static RetrieveFootballDataTask retrieveFootballDataTask;
    private UserChoices userChoices;
    private NavigationViewController navigationViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startingscreen);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //if there was a previous activity, get it
            userChoices = (UserChoices) extras.get("UserChoices");
            if (userChoices == null) {
                //if userchoices was null for whatever reason, create new
                System.out.println("xyy");
                userChoices = new UserChoices();
                new GameInformation();
            }
        }
        else{
            System.out.println("Xxxx");
            //if there was not a previous activity, create new
            userChoices = new UserChoices();
            new GameInformation();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        toolbar.setSubtitle("Football Squares");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        navigationViewController = new NavigationViewController(navigationView, userChoices, R.id.nav_home);
/*
        if (retrieveFootballDataTask == null || retrieveFootballDataTask.getStatus() != AsyncTask.Status.RUNNING) {

            Log.d("StartScreenAsync", "executed");

            retrieveFootballDataTask = new RetrieveFootballDataTask() {
                @Override
                protected void onPostExecute(final String s) {
                    GameInformation.webSourceCode = s;
                }
            };
            retrieveFootballDataTask.execute();
        }
*/
        Button continueButton = (Button)findViewById(R.id.continueGame);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartingScreenActivity.this, NameInputActivity.class);
                i.putExtra("UserChoices", userChoices);
                startActivity(i);
            }
        });


        Button mCreateGame = (Button) findViewById(R.id.createGame);
        mCreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userChoices.getArrayOfNames().size() > 0 || userChoices.getNamesOnBoard().size() > 0 || userChoices.getGame() != null) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartingScreenActivity.this);
                    alertDialogBuilder.setTitle("Are you sure?");

                    alertDialogBuilder.setMessage("Creating a new game will get rid of all the data you have in the current game.");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            RecursiveRetrieveScoreTask.endRecursion();
                            Intent i = new Intent(StartingScreenActivity.this, NameInputActivity.class);
                            userChoices = new UserChoices();
                            i.putExtra("UserChoices", userChoices);
                            startActivity(i);
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //does nothing
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else{
                    Intent i = new Intent(StartingScreenActivity.this, NameInputActivity.class);
                    i.putExtra("UserChoices", userChoices);
                    startActivity(i);
                }
            }
        });

        if (userChoices.getArrayOfNames().size() > 0 || userChoices.getNamesOnBoard().size() > 0 || userChoices.getGame() != null) {
            continueButton.setVisibility(View.VISIBLE);
            mCreateGame.setText("New Game");
        }

        Button loadGame = (Button)findViewById(R.id.loadGame);
        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartingScreenActivity.this, LoadGameActivity.class);
                i.putExtra("UserChoices", userChoices);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
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
        new NavigateOnMenuItemSelected(item, StartingScreenActivity.this, userChoices, R.id.nav_home);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
