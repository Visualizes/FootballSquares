package com.visual.android.footballsquares;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rami on 12/14/2015.
 */
public class GameSelectionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private GameSelectionAdapter gameSelectionAdapter;
    private List<Game> games;
    private RetrieveAllGames retrieveAllGames;
    private ListView lv;
    private ProgressBar progressBar;
    private TextView emptyPlaceholderView;
    private UserChoices userChoices;
    private NavigationViewController navigationViewController;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (GameInformation.retrieveFootballDataTask != null) {
            GameInformation.retrieveFootballDataTask.cancel(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameselection);

        Bundle extras = getIntent().getExtras();
        userChoices = (UserChoices)extras.get("UserChoices");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Game");
        toolbar.setSubtitle("Football Squares");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_game);

        navigationViewController = new NavigationViewController(navigationView, userChoices, R.id.nav_game);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        games = new ArrayList<>();

        GameInformation.retrieveFootballDataTask = new RetrieveFootballDataTask() {
            @Override
            protected void onPostExecute(String webSourceCode) {

                retrieveAllGames = new RetrieveAllGames(webSourceCode);
                games = retrieveAllGames.getGames();

                lv = (ListView) findViewById(R.id.listviewts);
                emptyPlaceholderView = (TextView) findViewById(R.id.emptyelement);
                lv.setEmptyView(emptyPlaceholderView);
                gameSelectionAdapter = new GameSelectionAdapter(GameSelectionActivity.this, games, userChoices);
                lv.setAdapter(gameSelectionAdapter);
                progressBar.setVisibility(View.GONE);
            }
        };
        GameInformation.retrieveFootballDataTask.execute();


    }//onCreate end


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gameselection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.refresh){

            progressBar.setVisibility(View.VISIBLE);
            emptyPlaceholderView.setText("");

            new RetrieveFootballDataTask() {
                @Override
                protected void onPostExecute(final String s) {
                    super.onPostExecute(s);

                    GameInformation.webSourceCode = s;

                    retrieveAllGames = new RetrieveAllGames(s);
                    games = retrieveAllGames.getGames();

                    emptyPlaceholderView.setText("No games found.");
                    lv.setEmptyView(emptyPlaceholderView);
                    gameSelectionAdapter = new GameSelectionAdapter(GameSelectionActivity.this, games, userChoices);
                    lv.setAdapter(gameSelectionAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }.execute(); //onPostExecute end
        }

        return super.onOptionsItemSelected(item);
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
        new NavigateOnMenuItemSelected(item, GameSelectionActivity.this, userChoices, R.id.nav_game);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}//class end