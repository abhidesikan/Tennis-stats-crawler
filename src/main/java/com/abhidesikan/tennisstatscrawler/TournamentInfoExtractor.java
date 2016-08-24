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
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by abhidesikan on 5/11/16.
 */
public class TournamentInfoExtractor {

    final Logger logger = LoggerFactory.getLogger(TournamentInfoExtractor.class);
    private List<ResultsArchive> resultsArchiveList = new ArrayList<ResultsArchive>();

    public void getTournamentInformationForAllYears() {
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int initialYear = 1915;
        for(; initialYear<= currentYear; initialYear++) {
            getTournamentInformationForYear(String.valueOf(initialYear));
        }

    }

    public void getTournamentInformationForYear(String year) {

        String tournamentUrl = "http://www.atpworldtour.com/en/scores/results-archive?year=";
        tournamentUrl = tournamentUrl.concat(year);
        logger.info("Extracting data for year "+year);

        try{
            Document doc = Jsoup.connect(tournamentUrl).timeout(10*1000).get();
            Elements elements = doc.select(".scores-results-archive");

            for(Element table: elements) {
                for(Element body: table.select("tbody")) {
                    for (Element tr : body.select("tr")) {
                        ResultsArchive resultsArchive = new ResultsArchive();
                        resultsArchive.setTournament(tr.select("span[class = tourney-title]").text());
                        resultsArchive.setDate(tr.select("span[class = tourney-dates]").text());
                        resultsArchive.setLocation(tr.select("span[class = tourney-location]").text());
                        String drawSpan = tr.select(".tourney-details> .info-area > .item-details > a[href]> span[class = item-value]").text();
                        if (!drawSpan.isEmpty()) {
                            if(drawSpan.split(" ").length > 1) {
                                resultsArchive.setDraw(new String[]{tr.select(".tourney-details> .info-area > .item-details > a[href]> span[class = item-value]").text().split(" ")[0],
                                        tr.select(".tourney-details> .info-area > .item-details > a[href]> span[class = item-value]").text().split(" ")[1]});
                            } else {
                                resultsArchive.setDraw(new String[]{tr.select(".tourney-details> .info-area > .item-details > a[href]> span[class = item-value]").text().split(" ")[0], ""});
                                }
                        }
                        resultsArchive.setTotalPrizeMoney(tr.select(".tourney-details.fin-commit > .info-area > .item-details > span[class = item-value]").text());
                        resultsArchive.setSurface(tr.select(".tourney-details > .info-area > .item-details").text().split(" ")[4] + " " + tr.select(".tourney-details > .info-area > .item-details").text().split(" ")[5]);
                        String winner = tr.select(".tourney-detail-winner").text();
                        String regex = Pattern.quote("SGL:") + "(.*?)" + Pattern.quote("DBL:");
                        String regex2 = "DBL:(.*)";

                        Pattern pattern = Pattern.compile(regex);
                        Pattern pattern2 = Pattern.compile(regex2);
                        Matcher matcher = pattern.matcher(winner);
                        Matcher matcher2 = pattern2.matcher(winner);
                        String singlesWinner = null, doublesWinner = null;
                        while (matcher.find()) {
                            singlesWinner = matcher.group(1);
                        }
                        while (matcher2.find()) {
                            doublesWinner = matcher2.group(1);
                        }
                        resultsArchive.setWinner(new String[]{singlesWinner, doublesWinner});
                        String url = tr.select((".tourney-details > .button-border")).attr("href");
                        if (!url.isEmpty()) {
                            resultsArchive.setTournamentCode(Integer.parseInt(url.split(("/"))[5]));
                        }
                        resultsArchiveList.add(resultsArchive);
                    }
                }
            }
            Parser.writeJSONToFile(resultsArchiveList, year);
            resultsArchiveList.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
