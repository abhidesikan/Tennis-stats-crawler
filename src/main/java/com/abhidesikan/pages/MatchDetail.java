package com.abhidesikan.pages;

/**
 * Created by abhidesikan on 10/15/16.
 */
public class MatchDetail {

    private String winnerSeed;
    private String winner;
    private String opponent;
    private String opponentSeed;
    private String score;


    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinnerSeed() {
        return winnerSeed;
    }

    public void setWinnerSeed(String winnerSeed) {
        this.winnerSeed = winnerSeed;
    }

    public String getOpponentSeed() {
        return opponentSeed;
    }

    public void setOpponentSeed(String opponentSeed) {
        this.opponentSeed = opponentSeed;
    }

    public String getOpponent() {

        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

}
