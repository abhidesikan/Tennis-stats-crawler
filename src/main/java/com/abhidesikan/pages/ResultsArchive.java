package com.abhidesikan.pages;

/**
 * Created by abhidesikan on 6/3/16.
 */


public class ResultsArchive {

    private String tournament;
    private String[] draw = new String[2];
    private String surface;
    private String totalPrizeMoney;
    private String[] winner = new String[2];
    private int tournamentCode;
    private String location;
    private String date;
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public String[] getDraw() {
        return draw;
    }

    public void setDraw(String[] draw) {
        this.draw = draw;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getTotalPrizeMoney() {
        return totalPrizeMoney;
    }

    public void setTotalPrizeMoney(String totalPrizeMoney) {
        this.totalPrizeMoney = totalPrizeMoney;
    }

    public String[] getWinner() {
        return winner;
    }

    public void setWinner(String[] winner) {
        this.winner = winner;
    }

    public int getTournamentCode() {
        return tournamentCode;
    }

    public void setTournamentCode(int tournamentCode) {
        this.tournamentCode = tournamentCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
