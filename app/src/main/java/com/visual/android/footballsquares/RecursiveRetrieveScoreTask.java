package com.visual.android.footballsquares;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by Rami on 12/22/2015.
 */
public class RecursiveRetrieveScoreTask extends RetrieveFootballDataTask {

    private MainBoardController mainBoardController;
    private ProgressBar mProgressBar;
    private UserChoices userChoices;

    public RecursiveRetrieveScoreTask(MainBoardController mainBoardController, ProgressBar mProgressBar, UserChoices userChoices){
        this.mainBoardController = mainBoardController;
        this.mProgressBar = mProgressBar;
        this.userChoices = userChoices;
    }

    protected void onPostExecute(final String webSourceCode){

        if (webSourceCode != null && !webSourceCode.equals("")) {

            //sets new game as current game in case new game doesn't exist (i.e. user loads an old game)
            Game newGame = userChoices.getGame();

            //gets last digit of home team score and away team score
            int currentHomeTeamScore = userChoices.getGame().getHomeTeamScore() % 10;
            int currentAwayTeamScore = userChoices.getGame().getAwayTeamScore() % 10;

            //gets all games
            RetrieveAllGames retrieveAllGames = new RetrieveAllGames(webSourceCode);
            List<Game> newGames = retrieveAllGames.getGames();

            //cycles through array of new games to find game that matches current
            for (Game element : newGames) {
                if (element.getNFLHomeTeamName().equals(userChoices.getGame().getNFLHomeTeamName())){
                    newGame = element;
                    break;
                }
            }

            //gets last digit of new home team and away team score
            int newHomeTeamScore = newGame.getHomeTeamScore() % 10;
            int newAwayTeamScore = newGame.getAwayTeamScore() % 10;

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mainBoardController.changeAnimationState(false);

            //checks to see if score has changed
            if (currentHomeTeamScore != newHomeTeamScore || currentAwayTeamScore != newAwayTeamScore) {
                //scores are not equal, update winner
                Log.d("Are they equal?", "false");

                mainBoardController.clearOldWinner(currentHomeTeamScore, currentAwayTeamScore);
                mainBoardController.showWinner(newHomeTeamScore, newAwayTeamScore);

                //updates game since scores changed
                userChoices.setGame(newGame);

                GameInformation.recursion++;
                GameInformation.webSourceCode = webSourceCode;

                GameInformation.recursiveRetrieveScoreTask = new RecursiveRetrieveScoreTask(mainBoardController, mProgressBar, userChoices);
                GameInformation.recursiveRetrieveScoreTask.execute();
            } else {
                //scores are equal
                Log.d("Are they equal?", "true");

                //the reason this is here is to show winner for the first instance if they're equal
                mainBoardController.showWinner(newHomeTeamScore, newAwayTeamScore);

                GameInformation.recursion++;
                GameInformation.webSourceCode = webSourceCode;

                GameInformation.recursiveRetrieveScoreTask = new RecursiveRetrieveScoreTask(mainBoardController, mProgressBar, userChoices);
                GameInformation.recursiveRetrieveScoreTask.execute();
            }

            mProgressBar.setVisibility(View.GONE);
        }
        else{
            System.out.println("WebSourceCode null");
            mainBoardController.clearAll();
            mProgressBar.setVisibility(View.VISIBLE);
            GameInformation.recursiveRetrieveScoreTask = new RecursiveRetrieveScoreTask(mainBoardController, mProgressBar, userChoices);
            GameInformation.recursiveRetrieveScoreTask.execute();
        }

    }//onPostExecute end


    public static void endRecursion(){
        if (GameInformation.recursiveRetrieveScoreTask != null) {
            System.out.println("RECURSION CANCELLED");
            GameInformation.recursiveRetrieveScoreTask.cancel(true);
        }
    }

}
