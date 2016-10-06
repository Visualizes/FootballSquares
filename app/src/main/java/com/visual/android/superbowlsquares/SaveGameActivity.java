package com.visual.android.superbowlsquares;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by RamiK on 8/19/2016.
 */
public class SaveGameActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private UserChoices userChoices;
    private NavigationViewController navigationViewController;
    private EditText saveDescription;

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

        saveDescription = (EditText) findViewById(R.id.saveDescription);
        final TextView textCounter = (TextView) findViewById(R.id.textCounter);
        final int textCount = Integer.valueOf(textCounter.getText().toString());

        textCounter.setText(String.valueOf(textCount - saveDescription.getText().toString().length()));

        saveDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO : Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textCounter.setText(String.valueOf(textCount - charSequence.length()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO : Auto-generated method stub
            }
        });

        saveDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    saveGame();
                }
                return false;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                Utils.hideKeyboard(SaveGameActivity.this);
            }
        };
        drawer.addDrawerListener(toggle);
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

    private void saveGame(){
        EditText saveName = (EditText) findViewById(R.id.saveName);
        final String name = saveName.getText().toString();
        final String description = saveDescription.getText().toString();

        if (!name.equals("")){

            LoadGame loadGame = new LoadGame(name, description);
            userChoices.setLoadGame(loadGame);

            final DatabaseHandler db = new DatabaseHandler(this);
            if (db.addLocalGame(userChoices)){
                Intent i = new Intent(this, StartingScreenActivity.class);
                i.putExtra("UserChoices", userChoices);
                startActivity(i);
                Toast.makeText(SaveGameActivity.this, "Game saved", Toast.LENGTH_SHORT).show();
            }
            else {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle("Are you sure?");
                dialogBuilder.setMessage("Game already exists. Would you like to overwrite it?");

                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.deleteLocalGame(userChoices.getLoadGame().getName());
                        db.addLocalGame(userChoices);
                        Intent i = new Intent(SaveGameActivity.this, StartingScreenActivity.class);
                        i.putExtra("UserChoices", userChoices);
                        startActivity(i);
                        Toast.makeText(SaveGameActivity.this, "Game saved", Toast.LENGTH_SHORT).show();

                    }
                });
                dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass
                    }
                });
                final AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        }
        else {
            Toast.makeText(SaveGameActivity.this, "Enter a name", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.savegame){
            saveGame();
        }

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