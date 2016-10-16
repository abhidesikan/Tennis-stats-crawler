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
            for(Element table : elements) {
                TournamentResultsArchive resultsArchive = new TournamentResultsArchive();
                for(Element head : table.select("thead")) {
                    for(Element tr : head.select("tr")) {
                        resultsArchive.setRound(tr.text());
                        resultsArchive.setMatchType(matchType);
                    }
                }
                for(Element body : table.select("tbody")) {
                    MatchDetail matchDetail = new MatchDetail();
                    for(Element tb : body.select("tr")) {
                        System.out.println(tb.select(".day-table-seed").text());
    //                    matchDetail.setWinnerSeed(Integer.parseInt(tb.select("span[class = day-table-seed]").text()));
                    }
                }
            }



        } catch (Exception e) {
            logger.error("Exception occurred " + e);
        }
    }
}
