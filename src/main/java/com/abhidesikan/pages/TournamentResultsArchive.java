package com.abhidesikan.pages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhidesikan on 10/15/16.
 */
public class TournamentResultsArchive {

    private String round;
    private String matchType;
    private List<MatchDetail> matchDetails = new ArrayList<MatchDetail>();

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public List<MatchDetail> getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(List<MatchDetail> matchDetails) {
        this.matchDetails = matchDetails;
    }
}
