package com.visual.android.superbowlsquares;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RamiK on 7/26/2016.
 */
public class JoinGameActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserChoices userChoices;
    private NavigationViewController navigationViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joingame);

        Bundle extras = getIntent().getExtras();
        userChoices = (UserChoices)extras.get("UserChoices");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Join Game");
        toolbar.setSubtitle("Football Squares");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                Utils.hideKeyboard(JoinGameActivity.this);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_join);
        navigationViewController = new NavigationViewController(navigationView, userChoices, R.id.nav_join);

        final DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Button joinGame = (Button) findViewById(R.id.confirm);
        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = (EditText) findViewById(R.id.code);
                if (!et.getText().toString().equals("")){
                    Query query =  mDatabaseReference.child("games").child(et.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserChoices userChoices = dataSnapshot.getValue(UserChoices.class);
                            if (userChoices != null) {
                                Toast.makeText(JoinGameActivity.this, "Game joined", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(JoinGameActivity.this, StartingScreenActivity.class);
                                i.putExtra("UserChoices", userChoices);
                                startActivity(i);
                            } else {
                                Toast.makeText(JoinGameActivity.this, "Error finding game: " + et.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //TODO: Auto-generated method stub
                            Toast.makeText(JoinGameActivity.this, "Error finding game: " + et.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else{
                    Toast.makeText(JoinGameActivity.this, "Please enter a code.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<String> convertJSONArrayToListSTR(JSONArray array){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            try {
                list.add(array.get(i).toString());
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return list;
    }

    private List<Integer> convertJSONArrayToListINT(JSONArray array){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            try {
                list.add(array.getInt(i));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return list;
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
        new NavigateOnMenuItemSelected(item, JoinGameActivity.this, userChoices, R.id.nav_join);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}