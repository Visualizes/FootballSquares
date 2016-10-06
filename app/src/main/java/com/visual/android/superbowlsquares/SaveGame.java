package com.visual.android.superbowlsquares;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rami on 2/16/2016.
 */
public class SaveGame {

    private String webSourceCode;
    private Game game;
    private int position;
    private List<String> arrayOfNames = new ArrayList<>();
    private List<String> namesOnBoard = new ArrayList<>();
    private List<Integer> row = new ArrayList<>();
    private List<Integer> column = new ArrayList<>();
    private List<Game> games;
    private int recursion = 0;
    private int dynamicPosition;
    private boolean isGameLocked = false;

    public SaveGame(String webSourceCode, Game game, int position, List<String> arrayOfNames, List<String> namesOnBoard, List<Integer> row, List<Integer> column,
                    List<Game> games, int recursion, int dynamicPosition, boolean isGameLocked){
        this.webSourceCode = webSourceCode;
        this.game = game;
        this.position = position;
        this.arrayOfNames = arrayOfNames;
        this.namesOnBoard = namesOnBoard;
        this.row = row;
        this.column = column;
        this.games = games;
        this.recursion = recursion;
        this.dynamicPosition = dynamicPosition;
        this.isGameLocked = isGameLocked;

    }

    public void execute(){
    }
}
