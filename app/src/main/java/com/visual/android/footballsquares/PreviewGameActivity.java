package com.visual.android.footballsquares;

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
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by RamiK on 5/12/2016.
 */
public class PreviewGameActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserChoices userChoices;
    private NavigationViewController navigationViewController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewgame);

        Bundle extras = getIntent().getExtras();
        userChoices = (UserChoices)extras.get("UserChoices");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Preview");
        toolbar.setSubtitle("Football Squares");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_save);

        navigationViewController = new NavigationViewController(navigationView, userChoices, R.id.nav_save);

        PreviewGame previewGame = new PreviewGame((this.getLayoutInflater().inflate(R.layout.content_preview, null)), this, userChoices);

        initializeBoard();

        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(previewGame.getArrayAdapter(R.layout.textview_savegame));

        TextView gameText = (TextView)findViewById(R.id.gametext);
        gameText.setText(previewGame.getGameText());
    }

    private void initializeBoard(){

        TextView[][] board = new TextView[10][10];
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
                Log.d("position", String.valueOf(position) + "i: " + i + "y: " + y);
                if (userChoices.getNamesOnBoard().size() < 100) {
                    userChoices.getNamesOnBoard().add("");
                }
                board[i][y] = (TextView) rows[i].getChildAt(y);
                board[i][y].setText(userChoices.getNamesOnBoard().get(position));
            }//loop y end
        }//loop i end
    }//method end

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

        if (id == R.id.nextButton){
            Intent i = new Intent(this, SaveGameActivity.class);
            i.putExtra("UserChoices", userChoices);
            startActivity(i);
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
        new NavigateOnMenuItemSelected(item, PreviewGameActivity.this, userChoices, R.id.nav_save);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
