package com.visual.android.footballsquares;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

/**
 * Created by RamiK on 8/19/2016.
 */
public class SaveGameActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private UserChoices userChoices;
    private NavigationViewController navigationViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savegame);

        Bundle extras = getIntent().getExtras();
        userChoices = (UserChoices) extras.get("UserChoices");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Save Game");
        toolbar.setSubtitle("Football Squares");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_save);

        navigationViewController = new NavigationViewController(navigationView, userChoices, R.id.nav_save);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_savegame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        if (id == R.id.savegame){
            final TinyDB tinyDB = new TinyDB(this);
            EditText saveName = (EditText) findViewById(R.id.saveName);
            EditText saveDescription = (EditText) findViewById(R.id.saveDescription);
            final String name = saveName.getText().toString();
            final String description = saveDescription.getText().toString();
            boolean doesNameAlreadyExist = false;

            @SuppressWarnings("unchecked")
            Map<String, UserChoices> allEntries = (Map<String,UserChoices>)((
                    tinyDB.getAll()));

            for (final Map.Entry<String, ?> entry : allEntries.entrySet()) {
                //Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                String[] x = entry.getKey().split(",");
                if (x[0].equals(name)){
                    doesNameAlreadyExist = true;
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SaveGameActivity.this);
                    dialogBuilder.setTitle("Are you sure?");
                    dialogBuilder.setMessage(name + " already exists. Do you want to override it?");
                    dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            tinyDB.remove(entry.getKey());
                            tinyDB.putObject(name + "," + description, userChoices);
                            Toast.makeText(SaveGameActivity.this, "Game saved", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SaveGameActivity.this, StartingScreenActivity.class);
                            i.putExtra("UserChoices", userChoices);
                            startActivity(i);
                        }
                    });
                    dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //pass
                        }
                    });
                    AlertDialog dialog = dialogBuilder.create();
                    dialog.show();
                    break;
                }
            }
            if (!doesNameAlreadyExist) {
                if (name.equals("")) {
                    Toast.makeText(SaveGameActivity.this, "Enter a name.", Toast.LENGTH_SHORT).show();
                } else {
                    tinyDB.putObject(name + "," + description, userChoices);
                    Toast.makeText(SaveGameActivity.this, "Game saved", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SaveGameActivity.this, StartingScreenActivity.class);
                    i.putExtra("UserChoices", userChoices);
                    startActivity(i);

                }
            }
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        new NavigateOnMenuItemSelected(item, SaveGameActivity.this, userChoices, R.id.nav_share);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}