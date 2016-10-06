package com.visual.android.superbowlsquares;

import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rami on 2/11/2016.
 */
public class NavigationViewController {

    private MenuItem boardSetUp;
    private MenuItem mainBoard;
    private MenuItem selectGame;
    private MenuItem saveGame;
    private MenuItem shareGame;
    private UserChoices userChoices;
    private static List<Integer> idOfNavItems = new ArrayList<>();
    private static List<NavigationView> listOfNavViews = new ArrayList<>();


    public void updateItemSelected(){
        listOfNavViews.get(1).setCheckedItem(idOfNavItems.get(1));
        listOfNavViews.remove(0);
        idOfNavItems.remove(0);
    }

    public NavigationViewController(NavigationView navigationView, UserChoices userChoices, int id){
        this.userChoices = userChoices;

        listOfNavViews.add(0, navigationView);
        idOfNavItems.add(0, id);

        Menu menu = navigationView.getMenu();

        boardSetUp = menu.findItem(R.id.nav_set_up);
        mainBoard = menu.findItem(R.id.nav_main);
        selectGame = menu.findItem(R.id.nav_game);
        saveGame = menu.findItem(R.id.nav_save);
        shareGame = menu.findItem(R.id.nav_share);

        enable();

    }

    public void enable(){
        boardSetUp.setEnabled(false);
        mainBoard.setEnabled(false);
        selectGame.setEnabled(false);
        saveGame.setEnabled(false);
        shareGame.setEnabled(false);

        if (userChoices.getArrayOfNames().size() > 1){
            boardSetUp.setEnabled(true);
        }
        if (boardSetUp.isEnabled() && userChoices.getNamesOnBoard().size() == 100){
            selectGame.setEnabled(true);
        }
        if (boardSetUp.isEnabled() && selectGame.isEnabled() && userChoices.getGame() != null){
            mainBoard.setEnabled(true);
            shareGame.setEnabled(true);
            saveGame.setEnabled(true);
        }
    }

    public boolean isMainBoardEnabled(){
        return mainBoard.isEnabled();
    }

    public boolean isSelectGameEnabled(){
        return selectGame.isEnabled();
    }

    public boolean isSaveGameEnabled(){
        return saveGame.isEnabled();
    }
}
