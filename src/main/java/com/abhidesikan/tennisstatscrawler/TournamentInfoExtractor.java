package com.abhidesikan.tennisstatscrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by abhidesikan on 5/11/16.
 */
public class TournamentInfoExtractor {

    final Logger logger = LoggerFactory.getLogger(TournamentInfoExtractor.class);

    private String tournamentUrl = "http://www.atpworldtour.com/en/scores/results-archive?year=";


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
                        System.out.println(tr.text());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
