package com.abhidesikan.pages;

/**
 * Created by abhidesikan on 10/15/16.
 */
public class MatchDetail {

    private int winnerSeed;
    private String winner;
    private String opponent;
    private int loserSeed;
    private String score;

    public int getWinnerSeed() {
        return winnerSeed;
    }

    public void setWinnerSeed(int winnerSeed) {
        this.winnerSeed = winnerSeed;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public int getLoserSeed() {
        return loserSeed;
    }

    public void setLoserSeed(int loserSeed) {
        this.loserSeed = loserSeed;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
