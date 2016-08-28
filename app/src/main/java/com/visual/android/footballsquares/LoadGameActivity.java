package com.visual.android.footballsquares;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rami on 2/12/2016.
 */
public class LoadGameActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private UserChoices userChoices;
    private NavigationViewController navigationViewController;
    private LoadGameAdapterTest2 loadGameAdapterTest2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadgame);

        Bundle extras = getIntent().getExtras();
        userChoices = (UserChoices)extras.get("UserChoices");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Load Game");
        toolbar.setSubtitle("Football Squares");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_load);
        navigationViewController = new NavigationViewController(navigationView, userChoices, R.id.nav_load);

        //new LoadGamesInBackground().execute();


    }
/*
    private class LoadGamesInBackground extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params){
            List<String> keyEntries = new ArrayList<>();
            TinyDB tinyDB = new TinyDB(LoadGameActivity.this);

            @SuppressWarnings("unchecked")
            Map<String, UserChoices> allEntries = (Map<String,UserChoices>)((tinyDB.getAll()));

            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                //Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                keyEntries.add(entry.getKey());
            }
            //loadGameAdapter = new LoadGameAdapter(LoadGameActivity.this, keyEntries);
            //loadGameAdapterTest = new LoadGameAdapterTest(LoadGameActivity.this, allEntries);
            loadGameAdapterTest2 = new LoadGameAdapterTest2(LoadGameActivity.this, keyEntries, userChoices);

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);
            GridView gridView = (GridView) findViewById(R.id.gridview);
            TextView emptyPlaceholderView = (TextView) findViewById(R.id.emptyelement);
            gridView.setEmptyView(emptyPlaceholderView);
            gridView.setAdapter(loadGameAdapterTest2);
            progressBar.setVisibility(View.GONE);
        }
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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
        new NavigateOnMenuItemSelected(item, LoadGameActivity.this, userChoices, R.id.nav_load);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

