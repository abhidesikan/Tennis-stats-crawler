package com.abhidesikan.tennisstatscrawler;

import com.abhidesikan.pages.MatchDetail;
import com.abhidesikan.pages.TournamentResultsArchive;
import com.sun.javadoc.Doc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by abhidesikan on 10/15/16.
 */
public class TournamentResultsExtractor {

    final Logger logger = LoggerFactory.getLogger(TournamentResultsExtractor.class);
    private List<TournamentResultsArchive> tournamentResultsArchiveList = new ArrayList<>();
    private static final String RESULTS_URL = "http://www.atpworldtour.com/en/scores/archive/";


    public void getTournamentResults(String tournamentName, int tournamentCode, String year, String matchType) {

        String resultsUrl;
        resultsUrl = RESULTS_URL + tournamentName.toLowerCase() + "/" + Integer.toString(tournamentCode) + "/" + year + "/" + "results?matchType=" + matchType.toLowerCase();
        logger.info("Extracting from url " + resultsUrl);

        try {
            Document document = Jsoup.connect(resultsUrl).timeout(10*1000).get();
            Elements elements = document.select(".scores-results-content");
            for (Element table : elements) {
                for (Element head : table.select("thead")) {
                    TournamentResultsArchive resultsArchive = new TournamentResultsArchive();
                    for (Element tr : head.select("tr")) {
                        resultsArchive.setRound(tr.text());
                        resultsArchive.setMatchType(matchType);
                    }
                    tournamentResultsArchiveList.add(resultsArchive);
                }
                int index = 0;
                for (Element body : table.select("tbody")) {
                    TournamentResultsArchive tournamentResultsArchive = tournamentResultsArchiveList.get(index);
                    List<MatchDetail> matchDetails = new ArrayList<>();
                    for (Element tb : body.select("tr")) {
                        MatchDetail matchDetail = new MatchDetail();
                        String winnerSeed = tb.select(".day-table-seed").get(0).text().replaceAll("[()]", "");
                        String loserSeed = tb.select(".day-table-seed").get(1).text().replaceAll("[()]", "");
                        matchDetail.setWinnerSeed(winnerSeed);
                        matchDetail.setOpponentSeed(loserSeed);
                        matchDetail.setWinner(tb.select(".day-table-name").get(0).text());
                        matchDetail.setOpponent(tb.select(".day-table-name").get(1).text());
                        String scores[] = tb.select(".day-table-score").text().split(" ");
                        String finalScore = "";
                        for (String score : scores) {
                            if(2 < score.length()) {
                                finalScore = finalScore + score.substring(0,2) + "(" + score.substring(2,score.length()) + ")" + " ";
                            } else {
                                finalScore = finalScore + score + " ";
                            }
                        }
                        matchDetail.setScore(finalScore);
                        matchDetails.add(matchDetail);
                    }
                    tournamentResultsArchive.setMatchDetails(matchDetails);
                    index++;
                }
            }
            for (TournamentResultsArchive resultsArchive : tournamentResultsArchiveList) {
                System.out.println(resultsArchive.getRound());
                List<MatchDetail> matchDetails = resultsArchive.getMatchDetails();
                for(MatchDetail matchDetail : matchDetails) {
                    System.out.println(matchDetail.getScore());
                    System.out.println(matchDetail.getWinner());
                }
            }

        } catch (Exception e) {
            logger.error("Exception occurred " + e);
        }
    }
}
