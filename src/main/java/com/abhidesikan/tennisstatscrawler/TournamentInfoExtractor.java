package com.abhidesikan.tennisstatscrawler;

import com.abhidesikan.pages.ResultsArchive;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhidesikan on 5/11/16.
 */
public class TournamentInfoExtractor {

    final Logger logger = LoggerFactory.getLogger(TournamentInfoExtractor.class);

    private String tournamentUrl = "http://www.atpworldtour.com/en/scores/results-archive?year=";
    private List<ResultsArchive> resultsArchiveList = new ArrayList<ResultsArchive>();

    public void getTournamentInformationForYear(String year) {

        tournamentUrl = tournamentUrl.concat(year);
        try{
            Document doc = Jsoup.connect(tournamentUrl).get();
            Elements elements = doc.select(".scores-results-archive");

            for(Element table: elements) {
                for(Element head: table.select("thead")) {
                    Elements tr = head.select("tr");
                    logger.info("Displaying header elements:");
                    for(Element th: tr.select("th")) {
                        System.out.println(th.text());
                    }
                }
                logger.info("Displaying body elements:");
                for(Element body: table.select("tbody")) {
                    for(Element tr: body.select("tr")) {
                        ResultsArchive resultsArchive = new ResultsArchive();
                        resultsArchive.setTournament(tr.select("span[class = tourney-title]").text());
                        resultsArchive.setDate(tr.select("span[class = tourney-dates]").text());
                        resultsArchive.setLocation(tr.select("span[class = tourney-location]").text());
                        String drawSpan = tr.select(".tourney-details> .info-area > .item-details > a[href]> span[class = item-value]").text();
                        if(!drawSpan.isEmpty()) {
                            resultsArchive.setDraw(new String[]{tr.select(".tourney-details> .info-area > .item-details > a[href]> span[class = item-value]").text().split(" ")[0],
                                    tr.select(".tourney-details> .info-area > .item-details > a[href]> span[class = item-value]").text().split(" ")[1]});
                        }
                        resultsArchive.setTotalPrizeMoney(tr.select(".tourney-details.fin-commit > .info-area > .item-details > span[class = item-value]").text());
                        System.out.println(resultsArchive.getTotalPrizeMoney());
                        resultsArchive.setSurface(tr.select(".tourney-details > .info-area > .item-details").text());
                        System.out.println(resultsArchive.getSurface());

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
