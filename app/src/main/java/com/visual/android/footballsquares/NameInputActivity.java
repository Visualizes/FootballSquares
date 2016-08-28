package com.visual.android.footballsquares;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Created by Rami on 12/16/2015.
 */
public class NameInputActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private UserChoices userChoices;
    private NavigationViewController navigationViewController;
    //private TourGuide mTourGuideHandler;
    //private SimpleTooltip simpleToolTip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nameinput);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //prevents keyboard from popping up on activity creation

        Bundle extras = getIntent().getExtras();
        userChoices = (UserChoices)extras.get("UserChoices");

        final EditText mUserInput = (EditText)findViewById(R.id.nameInput);
        //mTourGuideHandler = TourGuide.init(NameInputActivity.this).with(TourGuide.Technique.Click);
/*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("X");
                mTourGuideHandler
                        .setPointer(new Pointer())
                        .setToolTip(new tourguide.tourguide.ToolTip()
                                .setTitle("Welcome!")
                                .setDescription("Click on Enter Name Here to begin...")
                                .setBackgroundColor(ContextCompat.getColor(NameInputActivity.this, R.color.orangelight)))
                        .setOverlay(new Overlay())
                        .playOn(mUserInput);
                mUserInput.getViewTreeObserver().dispatchOnGlobalLayout();
            }
        }, 150);
*/
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_add_people);

        navigationViewController = new NavigationViewController(navigationView, userChoices, R.id.nav_add_people);

        final NameInputAdapter nameInputAdapter = new NameInputAdapter(this, userChoices.getArrayOfNames(), navigationViewController, userChoices);
        final ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(nameInputAdapter);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Name Input");
        toolbar.setSubtitle("Football Squares");

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //simpleToolTip.dismiss();

                if (mUserInput.getText().toString().equals("")) {
                    Snackbar.make(view, "Please enter a name.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                } else {
                    userChoices.getArrayOfNames().add(nameInputAdapter.getStoredPosition(), mUserInput.getText().toString());
                    nameInputAdapter.resetStoredPosition();
                    mUserInput.setText("");
                    nameInputAdapter.notifyDataSetChanged();
                    listView.post(new Runnable() {
                        @Override
                        public void run() {/*
                            View view = NameInputActivity.this.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }

                            // sequence example
                            ShowcaseConfig config = new ShowcaseConfig();
                            config.setDelay(100); // half second between each showcase view
                            config.setMaskColor(ContextCompat.getColor(NameInputActivity.this, R.color.orangetrans));

                            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(NameInputActivity.this);

                            sequence.setConfig(config);

                            sequence.addSequenceItem((listView.getChildAt(0)).findViewById(R.id.editName),
                                    "Use this to edit the player's name.", "GOT IT");

                            sequence.addSequenceItem((listView.getChildAt(0)).findViewById(R.id.removeName),
                                    "Use this to delete the player's name.", "GOT IT");

                            sequence.addSequenceItem(findViewById(R.id.nextButton),
                                    "When you're done adding all the players, click this to continue to the next page.", "GOT IT");

                            sequence.start();*/

                        }
                    });

                    new NavigationViewController(navigationView, userChoices, R.id.nav_add_people);
                }
            }
        });

        mUserInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mTourGuideHandler.cleanUp();
            }
        });

        mUserInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO : Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
/*
                    simpleToolTip = new SimpleTooltip.Builder(NameInputActivity.this)
                            .anchorView(fab)
                            .dismissOnOutsideTouch(false)
                            .dismissOnInsideTouch(false)
                            .maxWidth(R.dimen.tooltip_max_width)
                            .text("Press this when you're done entering a name to add it.")
                            .textColor(ContextCompat.getColor(NameInputActivity.this, R.color.white))
                            .backgroundColor(ContextCompat.getColor(NameInputActivity.this, R.color.orangelight))
                            .arrowColor(ContextCompat.getColor(NameInputActivity.this, R.color.orangelight))
                            .gravity(Gravity.START)
                            .animated(true)
                            .transparentOverlay(true)
                            .build();
                    simpleToolTip.show();*/
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO : Auto-generated method stub
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {/*
            if (userChoices.getArrayOfNames().size() > 0){
                Intent i = new Intent(NameInputActivity.this, StartingScreenActivity.class);
                i.putExtra("UserChoices", userChoices);
                startActivity(i);
            }
            else{*/
                super.onBackPressed();
            //}
            navigationViewController.updateItemSelected();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.nextButton){
            if (userChoices.getArrayOfNames().size() > 1) {
                Intent i = new Intent(this, BoardSetUpActivity.class);
                i.putExtra("UserChoices", userChoices);
                startActivity(i);
            }
            else{
                Toast.makeText(NameInputActivity.this, "Not enough players. (2 minimum)", Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        new NavigateOnMenuItemSelected(item, NameInputActivity.this, userChoices, R.id.nav_add_people);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
