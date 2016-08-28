package com.visual.android.footballsquares;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
/**
 * Created by Rami on 12/16/2015.
 */
public class MainBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private UserChoices userChoices;
    private MainBoardController mainBoardController;
    private ProgressBar mProgressBar;
    private NavigationViewController navigationViewController;
    private boolean getLiveScores;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("GAME DESTROYED!!");
        RecursiveRetrieveScoreTask.endRecursion();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("GAME PAUSED!!");
        RecursiveRetrieveScoreTask.endRecursion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        executeRecursiveRetrieveScoreTask();
        System.out.println("GAME RESUMED!!");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainboard);

        Bundle extras = getIntent().getExtras();
        userChoices = (UserChoices)extras.get("UserChoices");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Board");
        toolbar.setSubtitle("Football Squares");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_main);
        navigationViewController = new NavigationViewController(navigationView, userChoices, R.id.nav_main);

        TableLayout layout = (TableLayout) findViewById(R.id.table);
        TextView[][] board = new TextView[10][10];
        TableRow[] rows = new TableRow[11];

        mainBoardController = new MainBoardController(layout, board, rows, userChoices);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);


    }//onCreate end

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        View.OnClickListener closeSnackBar = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //closes snackbar
            }
        };

        if (id == R.id.show_scores){
            getLiveScores = true;
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_scores, null);
            dialogBuilder.setView(dialogView);
            updateLiveScoreText(dialogView);
            recursivelyGetScores(dialogView);
            AlertDialog dialog = dialogBuilder.create();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    getLiveScores = false;
                }
            });
            dialog.show();
        }

        if (id == R.id.show_teams){
            Snackbar.make(findViewById(android.R.id.content), userChoices.getGame().getNFLHomeTeamName() + " (ROW) vs. "
                    + userChoices.getGame().getNFLAwayTeamName() + " (COLUMN)", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Got it!", closeSnackBar)
                    .setActionTextColor(ContextCompat.getColor(this, R.color.green))
                    .show();
        }

        if (id == R.id.respin){
            if (!mProgressBar.isShown()) {
                RecursiveRetrieveScoreTask.endRecursion();
                mainBoardController.clearOldWinner(userChoices.getGame().getHomeTeamScore() % 10, userChoices.getGame().getAwayTeamScore() % 10);
                mProgressBar.setVisibility(View.VISIBLE);
                mainBoardController.changeAnimationState(true);
                mainBoardController.animateAndSetRowCol();
                executeRecursiveRetrieveScoreTask();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void recursivelyGetScores(final View dialogView){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (getLiveScores) {
                    updateLiveScoreText(dialogView);
                    recursivelyGetScores(dialogView);
                }
            }
        }, 2000);
    }

    private void updateLiveScoreText(View dialogView){
        TextView homeTeamName = (TextView) dialogView.findViewById(R.id.homeTeamName);
        TextView homeTeamScore = (TextView) dialogView.findViewById(R.id.homeTeamScore);
        TextView awayTeamName = (TextView) dialogView.findViewById(R.id.awayTeamName);
        TextView awayTeamScore = (TextView) dialogView.findViewById(R.id.awayTeamScore);

        homeTeamName.setText(userChoices.getGame().getNFLHomeTeamName());
        homeTeamScore.setText(String.valueOf(userChoices.getGame().getHomeTeamScore()));
        awayTeamName.setText(userChoices.getGame().getNFLAwayTeamName());
        awayTeamScore.setText(String.valueOf(userChoices.getGame().getAwayTeamScore()));
    }

    private void executeRecursiveRetrieveScoreTask(){
        GameInformation.recursiveRetrieveScoreTask = new RecursiveRetrieveScoreTask(mainBoardController, mProgressBar, userChoices);
        if (GameInformation.recursiveRetrieveScoreTask.getStatus() != AsyncTask.Status.RUNNING){
            GameInformation.recursiveRetrieveScoreTask.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainboard, menu);
        return true;
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
        new NavigateOnMenuItemSelected(item, MainBoardActivity.this, userChoices, R.id.nav_main);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}//class end
