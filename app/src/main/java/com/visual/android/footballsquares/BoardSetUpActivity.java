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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.visual.android.footballsquares.BoardSetUpOnClickListener;
import com.visual.android.footballsquares.NavigateOnMenuItemSelected;
import com.visual.android.footballsquares.NavigationViewController;
import com.visual.android.footballsquares.Pair;
import com.visual.android.footballsquares.R;
import com.visual.android.footballsquares.UserChoices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rami on 12/16/2015.
 */
public class BoardSetUpActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BoardSetUpOnClickListener onClickBoardData;
    private TextView[][] board = new TextView[10][10];
    private UserChoices userChoices;
    private NavigationViewController navigationViewController;
    private Toolbar toolbar;
    private Menu menu;
    private int menuInflation = R.menu.menu_boardsetup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardsetup);

        Bundle extras = getIntent().getExtras();
        userChoices = (UserChoices)extras.get("UserChoices");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Football Squares");

        initializeDrawer();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_set_up);

        initializeBoard();

        navigationViewController = new NavigationViewController(navigationView, userChoices, R.id.nav_set_up);

        getSupportActionBar().setTitle("It's " + userChoices.getArrayOfNames().get(BoardSetUpOnClickListener.nameIndex) + "'s turn.");

    }//onCreate end



    private void initializeBoard(){

        View view = findViewById(R.id.board);
        TableRow[] rows = new TableRow[10];
        TableLayout layout = (TableLayout) view.findViewById(R.id.table);

        for (int i = 0; i < 10; i++){
            rows[i] = (TableRow)layout.getChildAt(i);
        }
        //sets the ints of the arrays equal to each respective child
        for (int i = 0; i < 10; i++){
            for (int y = 0; y < 10; y++) {
                int position = (i*10) + y;
                if (userChoices.getNamesOnBoard().size() < 100) {
                    userChoices.getNamesOnBoard().add("");
                }
                board[i][y] = (TextView) rows[i].getChildAt(y);
                board[i][y].setText(userChoices.getNamesOnBoard().get(position));

                onClickBoardData = new BoardSetUpOnClickListener(i, y, getSupportActionBar(), userChoices);
                board[i][y].setOnClickListener(onClickBoardData);
            }//loop y end
        }//loop i end
    }//method end

    private void initializeDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menuInflation, menu);
        if (BoardSetUpOnClickListener.isFrozen && !BoardSetUpOnClickListener.deleteMode) {
            menu.getItem(3).setTitle("Unfreeze\nTurn");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.delete){
            BoardSetUpOnClickListener.deleteMode = true;
            menuInflation = R.menu.menu_boardsetup_delete;
            onCreateOptionsMenu(menu);
            setSupportActionBar(toolbar);
            toolbar.setTitle("Press a name to delete it.");
            initializeDrawer();
        }

        if (id == R.id.checkmark){
            BoardSetUpOnClickListener.deleteMode = false;
            menuInflation = R.menu.menu_boardsetup;
            onCreateOptionsMenu(menu);
            setSupportActionBar(toolbar);
            toolbar.setTitle("It's " + userChoices.getArrayOfNames().get(BoardSetUpOnClickListener.nameIndex) + "'s turn.");
            initializeDrawer();
        }

        if (id == R.id.fillboard){
            List<Pair> listOfPairs = new ArrayList<>();
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    listOfPairs.add(new Pair(x,y));
                }
            }
            Collections.shuffle(listOfPairs);
            int position = 0;
            for (int i = 0; i < 100; i++){
                int xCoordinate = listOfPairs.get(i).getxCoordinate();
                int yCoordinate = listOfPairs.get(i).getyCoordinate();
                int indexOfNamesOnBoardArray = (xCoordinate * 10) + yCoordinate;
                board[xCoordinate][yCoordinate].setText(userChoices.getArrayOfNames().get(position));
                userChoices.getNamesOnBoard().set(indexOfNamesOnBoardArray, userChoices.getArrayOfNames().get(position));
                position++;
                if (position == userChoices.getArrayOfNames().size()){
                    position = 0;
                }
            }
        }

        if (id == R.id.skipturn){
            BoardSetUpOnClickListener.nameIndex++;
            if (BoardSetUpOnClickListener.nameIndex == userChoices.getArrayOfNames().size()) {
                BoardSetUpOnClickListener.nameIndex = 0;
            }

            getSupportActionBar().setTitle("It's " + userChoices.getArrayOfNames().get(onClickBoardData.nameIndex) + "'s turn.");
        }

        if (id == R.id.freezeturn){
            if (!BoardSetUpOnClickListener.isFrozen) {
                BoardSetUpOnClickListener.isFrozen = true;
                item.setTitle("Unfreeze\nTurn");

            } else {
                BoardSetUpOnClickListener.isFrozen = false;
                item.setTitle("Freeze\nTurn");
            }
        }

        if (id == R.id.next){
            if (userChoices.getNamesOnBoard().contains("")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BoardSetUpActivity.this);
                alertDialogBuilder.setTitle("Are you sure you want to continue?");

                alertDialogBuilder.setMessage("You have not finished filling in the board entirely.");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(BoardSetUpActivity.this, GameSelectionActivity.class);
                        i.putExtra("UserChoices", userChoices);
                        startActivity(i);
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            else{
                Intent i = new Intent(BoardSetUpActivity.this, GameSelectionActivity.class);
                i.putExtra("UserChoices", userChoices);
                startActivity(i);
            }
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
        new NavigateOnMenuItemSelected(item, BoardSetUpActivity.this, userChoices, R.id.nav_set_up);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}//class end
