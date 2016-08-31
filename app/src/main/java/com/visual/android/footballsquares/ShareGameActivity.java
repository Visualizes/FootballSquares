package com.visual.android.footballsquares;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.List;

/**
 * Created by RamiK on 7/26/2016.
 */
public class ShareGameActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserChoices userChoices;
    private byte bytes[];
    private NavigationViewController navigationViewController;
    private ProgressBar progressBar;
    private Button retry;
    private TextView code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharegame);

        Bundle extras = getIntent().getExtras();
        userChoices = (UserChoices) extras.get("UserChoices");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Share Game");
        toolbar.setSubtitle("Football Squares");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_share);
        navigationViewController = new NavigationViewController(navigationView, userChoices, R.id.nav_share);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        retry = (Button) findViewById(R.id.retryButton);
        code = (TextView) findViewById(R.id.code);

        SecureRandom random = new SecureRandom();
        bytes = new byte[20];
        random.nextBytes(bytes);
        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("UUID", bytes.toString().substring(3));
            jsonObject.put("HomeTeamName", userChoices.getGame().getNFLHomeTeamName());
            jsonObject.put("AwayTeamName", userChoices.getGame().getNFLAwayTeamName());
            jsonObject.put("HomeTeamScore", userChoices.getGame().getHomeTeamScore());
            jsonObject.put("AwayTeamScore", userChoices.getGame().getAwayTeamScore());
            jsonObject.put("DateOfGame", userChoices.getGame().getDate());
            jsonObject.put("Names", strArrayToJSON(userChoices.getArrayOfNames()));
            jsonObject.put("NamesOnBoard", strArrayToJSON(userChoices.getNamesOnBoard()));
            jsonObject.put("Row", intArrayToJSON(userChoices.getRow()));
            jsonObject.put("Col", intArrayToJSON(userChoices.getColumn()));
            shareGameWithTriton(jsonObject);

        } catch (JSONException e){
            e.printStackTrace();
        }

        ImageButton sms = (ImageButton) findViewById(R.id.share_sms);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", "yo");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(ShareGameActivity.this);
                    sendIntent.setPackage(defaultSmsPackageName);
                }
                startActivity(sendIntent);
            }
        });

        ImageButton messenger = (ImageButton) findViewById(R.id.share_messenger);
        messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", "DIS IS TEST");
                sendIntent.setPackage("com.facebook.orca");
                try {
                    startActivity(sendIntent);
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ShareGameActivity.this, "Please install Facebook Messenger", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageButton copy = (ImageButton) findViewById(R.id.share_copy);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                if (!code.getText().equals("")) {
                    ClipData clip = ClipData.newPlainText("Copy Code", code.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ShareGameActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendSMS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "text");

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        }
        else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address","phoneNumber");
            smsIntent.putExtra("sms_body","message");
            startActivity(smsIntent);
        }
    }

    private void shareGameWithTriton(final JSONObject jsonObject){
        new Triton("put").execute(new Callback() {
            @Override
            public void call(JSONObject array) {
                try {
                    if (array.getInt("responseCode") == 200) {
                        progressBar.setVisibility(View.GONE);
                        code.setText(bytes.toString().substring(3));
                        code.setTypeface(null, Typeface.BOLD);
                    }
                    else {
                        retry.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        retry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                retry.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.VISIBLE);
                                shareGameWithTriton(jsonObject);
                            }
                        });
                    }
                } catch (JSONException e){
                    e.printStackTrace();


                }
            }
        }, jsonObject.toString());
    }

    private JSONArray strArrayToJSON(List<String> array){
        JSONArray jsonArray = new JSONArray();
        for (String element : array){
            jsonArray.put(element);
        }
        return jsonArray;
    }

    private JSONArray intArrayToJSON(List<Integer> array){
        JSONArray jsonArray = new JSONArray();
        for (int element : array){
            jsonArray.put(element);
        }
        return jsonArray;
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
        new NavigateOnMenuItemSelected(item, ShareGameActivity.this, userChoices, R.id.nav_share);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
