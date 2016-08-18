package com.abhidesikan.tennisstatscrawler;

import com.abhidesikan.tennistippingpredictor.OddsExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by abhidesikan on 5/6/16.
 */
public class Main {

    final static Logger logger = LoggerFactory.getLogger(Main.class);

    final static String resultsUrl = "http://www.atpworldtour.com/en/scores/results-archive?year=";



    /*
        Proof of concept
     */

    public static void testJSoup(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select(".scores-results-archive");

            for(Element table: elements) {
                for(Element head: table.select("thead")) {
                    Elements tr = head.select("tr");
                    logger.info("Displaying header elements:");
                    for(Element th: tr.select("th")) {
                        System.out.println(th.text());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) throws Exception{
       // testJSoup("http://www.atpworldtour.com/en/scores/results-archive?year=2016");
        TournamentInfoExtractor infoExtractor = new TournamentInfoExtractor();
        OddsExtractor oddsExtractor = new OddsExtractor();
//        infoExtractor.getTournamentInformationForYear("2011");
        infoExtractor.getTournamentInformationForAllYears();

    }
}
