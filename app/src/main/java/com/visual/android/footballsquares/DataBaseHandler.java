package com.visual.android.footballsquares;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by RamiK on 8/28/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "GameData";

    // Contacts table name
    private static final String TABLE_USERCHOICES = "Userchoices";

    // Contacts Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_ROW = "row";
    private static final String KEY_COLUMN = "col";
    private static final String KEY_NAMES = "names";
    private static final String KEY_NAMES_ON_BOARD = "namesOnBoard";
    private static final String KEY_HOME_TEAM_NAME = "homeTeamName";
    private static final String KEY_HOME_TEAM_SCORE = "homeTeamScore";
    private static final String KEY_AWAY_TEAM_NAME = "awayTeamName";
    private static final String KEY_AWAY_TEAM_SCORE = "awayTeamScore";
    private static final String KEY_GAME_DATE = "dateOfGame";

    private Type typeString;
    private Type typeInteger;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        typeString = new TypeToken<ArrayList<String>>() {}.getType();
        typeInteger = new TypeToken<ArrayList<Integer>>() {}.getType();
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERCHOICES + "("
                + KEY_NAME + " TEXT PRIMARY KEY," + KEY_DESCRIPTION + " TEXT,"
                + KEY_ROW + " TEXT," + KEY_COLUMN + " TEXT," + KEY_NAMES + " TEXT,"
                + KEY_NAMES_ON_BOARD + " TEXT," + KEY_HOME_TEAM_NAME + " TEXT,"
                + KEY_AWAY_TEAM_NAME + " TEXT," + KEY_HOME_TEAM_SCORE + " INTEGER,"
                + KEY_AWAY_TEAM_SCORE + " INTEGER," + KEY_GAME_DATE + " TEXT" + ")";
        System.out.println(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERCHOICES);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public boolean addLocalGame (final UserChoices userChoices) {
        boolean responseCode = true;
        SQLiteDatabase db = this.getWritableDatabase();

        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, userChoices.getLoadGame().getName()); // Name
        values.put(KEY_DESCRIPTION, userChoices.getLoadGame().getDescription()); // Description
        values.put(KEY_ROW, gson.toJson(userChoices.getRow()));
        values.put(KEY_COLUMN, gson.toJson(userChoices.getColumn()));
        values.put(KEY_NAMES, gson.toJson(userChoices.getArrayOfNames()));
        values.put(KEY_NAMES_ON_BOARD, gson.toJson(userChoices.getNamesOnBoard()));
        values.put(KEY_HOME_TEAM_NAME, userChoices.getGame().getNFLHomeTeamName());
        values.put(KEY_AWAY_TEAM_NAME, userChoices.getGame().getNFLAwayTeamName());
        values.put(KEY_HOME_TEAM_SCORE, userChoices.getGame().getHomeTeamScore());
        values.put(KEY_AWAY_TEAM_SCORE, userChoices.getGame().getAwayTeamScore());
        values.put(KEY_GAME_DATE, userChoices.getGame().getDate());

        // Inserting Row
        try {
            db.insertOrThrow(TABLE_USERCHOICES, null, values);
        } catch (SQLiteConstraintException e){
            e.printStackTrace();
            responseCode = false;
        }
        db.close(); // Closing database connection
        return responseCode;
    }

    // Getting single contact
    public UserChoices getLocalGame(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Gson gson = new Gson();

        Cursor cursor = db.query(TABLE_USERCHOICES, new String[] { KEY_NAME,
                        KEY_NAME, KEY_ROW, KEY_COLUMN, KEY_NAMES, KEY_NAMES_ON_BOARD,
                        KEY_HOME_TEAM_NAME, KEY_AWAY_TEAM_NAME, KEY_HOME_TEAM_SCORE,
                        KEY_AWAY_TEAM_SCORE, KEY_GAME_DATE}, KEY_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        UserChoices userChoices = new UserChoices();
        userChoices.setLoadGame(new LoadGame(cursor.getString(0), cursor.getString(1)));
        userChoices.setRow((List<Integer>)gson.fromJson(cursor.getString(2), typeInteger));
        userChoices.setColumn((List<Integer>)gson.fromJson(cursor.getString(3), typeInteger));
        userChoices.setArrayOfNames((List<String>)gson.fromJson(cursor.getString(4), typeString));
        userChoices.setNamesOnBoard((List<String>)gson.fromJson(cursor.getString(5), typeString));
        userChoices.setGame(new Game(cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10)));

        // return contact
        return userChoices;
    }

    // Getting All Contacts
    public List<UserChoices> getAllContacts() {
        List<UserChoices> localGames = new ArrayList<>();
        Gson gson = new Gson();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERCHOICES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserChoices userChoices = new UserChoices();
                userChoices.setLoadGame(new LoadGame(cursor.getString(0), cursor.getString(1)));
                userChoices.setRow((List<Integer>)gson.fromJson(cursor.getString(2), typeInteger));
                userChoices.setColumn((List<Integer>)gson.fromJson(cursor.getString(3), typeInteger));
                userChoices.setArrayOfNames((List<String>)gson.fromJson(cursor.getString(4), typeString));
                userChoices.setNamesOnBoard((List<String>)gson.fromJson(cursor.getString(5), typeString));
                userChoices.setGame(new Game(cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10)));
                // Adding contact to list
                localGames.add(userChoices);
            } while (cursor.moveToNext());
        }

        Collections.sort(localGames, new Comparator<UserChoices>() {
            public int compare(UserChoices o1, UserChoices o2) {
                return o1.getLoadGame().getName().compareTo(o2.getLoadGame().getName());
            }
        });

        // return contact list
        return localGames;
    }

    // Updating single contact
    public int updateLocalGame(UserChoices userChoices) {
        SQLiteDatabase db = this.getWritableDatabase();
        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, userChoices.getLoadGame().getName()); // Name
        values.put(KEY_DESCRIPTION, userChoices.getLoadGame().getDescription()); // Description
        values.put(KEY_ROW, gson.toJson(userChoices.getRow()));
        values.put(KEY_COLUMN, gson.toJson(userChoices.getColumn()));
        values.put(KEY_NAMES, gson.toJson(userChoices.getArrayOfNames()));
        values.put(KEY_NAMES_ON_BOARD, gson.toJson(userChoices.getNamesOnBoard()));
        values.put(KEY_HOME_TEAM_NAME, userChoices.getGame().getNFLHomeTeamName());
        values.put(KEY_AWAY_TEAM_NAME, userChoices.getGame().getNFLAwayTeamName());
        values.put(KEY_HOME_TEAM_SCORE, userChoices.getGame().getHomeTeamScore());
        values.put(KEY_AWAY_TEAM_SCORE, userChoices.getGame().getAwayTeamScore());
        values.put(KEY_GAME_DATE, userChoices.getGame().getDate());

        // updating row
        return db.update(TABLE_USERCHOICES, values, KEY_NAME + " = ?",
                new String[] { userChoices.getLoadGame().getName() });
    }

    // Deleting single contact
    public void deleteLocalGame(String uniqueName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERCHOICES, KEY_NAME + " = ?",
                new String[] { uniqueName });
        db.close();
    }


    // Getting contacts Count
    public int getLocalGamesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USERCHOICES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cursorCount = cursor.getCount();
        cursor.close();

        // return count
        return cursorCount;
    }


}
