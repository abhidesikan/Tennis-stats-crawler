package com.abhidesikan.tennisstatscrawler;

import com.abhidesikan.pages.ResultsArchive;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhidesikan on 8/16/16.
 */
public class Parser {

    final static Logger logger = LoggerFactory.getLogger(Parser.class);
    private static final String RESULTS_ARCHIVE_DIR = "ResultsArchiveFiles";
    private static final String RESULTS_ARCHIVE_FILE = "_tournament_results.json";

    public static void writeJSONToFile(List<ResultsArchive> resultsArchiveList, String year) {
        List<JSONObject> objList = new ArrayList<>();
        for(ResultsArchive result : resultsArchiveList) {
            JSONObject tournamentObject = new JSONObject();
            tournamentObject.put("Tournament", result.getTournament());
            tournamentObject.put("Surface", result.getSurface());
            tournamentObject.put("Tournament code", result.getTournamentCode());
            tournamentObject.put("Total prize money", result.getTotalPrizeMoney());
            tournamentObject.put("Location", result.getLocation());
            tournamentObject.put("Date", result.getDate());

            JSONArray drawArray = new JSONArray();
            JSONObject draw = new JSONObject();
            draw.put("SGL", result.getDraw()[0]);
            draw.put("DBL", result.getDraw()[1]);
            drawArray.add(draw);
            tournamentObject.put("Draw", drawArray);

            JSONArray winnerArray = new JSONArray();
            JSONObject winner = new JSONObject();
            winner.put("SGL", result.getWinner()[0]);
            winner.put("DBL", result.getWinner()[1]);
            winnerArray.add(winner);
            tournamentObject.put("Winner", winnerArray);

            objList.add(tournamentObject);
        }

        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(RESULTS_ARCHIVE_DIR+"/"+year+RESULTS_ARCHIVE_FILE))) {
            for(JSONObject tournamentObject : objList) {
                writer.write(tournamentObject.toJSONString() + "\n");
            }
            objList.clear();
        } catch (IOException e) {
            logger.error("Unable to write to file "+e);
        }
    }
}
