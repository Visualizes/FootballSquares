package com.visual.android.superbowlsquares;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rami on 1/20/2016.
 */
public class UserChoices implements Serializable{

    public Game game;
    public List<String> arrayOfNames = new ArrayList<>();
    public List<String> namesOnBoard = new ArrayList<>();
    public List<Integer> row = new ArrayList<>();
    public List<Integer> column = new ArrayList<>();
    private LoadGame loadGame;

    public UserChoices(){

    }

    public UserChoices(Game game, List<String> arrayOfNames, List<String> namesOnBoard, List<Integer> row, List<Integer> column, LoadGame loadGame){
        this.game = game;
        this.arrayOfNames = arrayOfNames;
        this.namesOnBoard = namesOnBoard;
        this.row = row;
        this.column = column;
        this.loadGame = loadGame;
    }

    public void setLoadGame(LoadGame loadGame) {
        this.loadGame = loadGame;
    }

    public LoadGame getLoadGame() {
        return loadGame;
    }

    public List<Integer> getRow() {
        return row;
    }

    public void setRow(List<Integer> row) {
        this.row = row;
    }

    public List<Integer> getColumn() {
        return column;
    }

    public void setColumn(List<Integer> column) {
        this.column = column;
    }

    public List<String> getNamesOnBoard() {
        return namesOnBoard;
    }

    public void setNamesOnBoard(List<String> namesOnBoard) {
        this.namesOnBoard = namesOnBoard;
    }

    public List<String> getArrayOfNames() {
        return arrayOfNames;
    }

    public void setArrayOfNames(List<String> arrayOfNames) {
        this.arrayOfNames = arrayOfNames;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
