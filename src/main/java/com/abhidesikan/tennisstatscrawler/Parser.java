package com.abhidesikan.tennisstatscrawler;

import com.abhidesikan.pages.TournamentInfoArchive;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhidesikan on 8/16/16.
 */
public class Parser {

    final static Logger logger = LoggerFactory.getLogger(Parser.class);
    public static final String TOURNAMENT_INFO_ARCHIVE_DIR = "TournamentInfoArchiveFiles";
    public static final String TOURNAMENT_INFO_ARCHIVE_FILE = "_tournament_info.json";

    public static void writeJSONToFile(List<TournamentInfoArchive> tournamentInfoArchiveList, String year) {
        List<JSONObject> objList = new ArrayList<>();
        for(TournamentInfoArchive infoArchive : tournamentInfoArchiveList) {
            JSONObject tournamentObject = new JSONObject();
            tournamentObject.put("Tournament", infoArchive.getTournament());
            tournamentObject.put("Surface", infoArchive.getSurface());
            tournamentObject.put("Tournament_code", infoArchive.getTournamentCode());
            tournamentObject.put("Total_prize_money", infoArchive.getTotalPrizeMoney());
            tournamentObject.put("Location", infoArchive.getLocation());
            tournamentObject.put("Date", infoArchive.getDate());

            JSONArray drawArray = new JSONArray();
            JSONObject draw = new JSONObject();
            draw.put("SGL", infoArchive.getDraw()[0]);
            draw.put("DBL", infoArchive.getDraw()[1]);
            drawArray.add(draw);
            tournamentObject.put("Draw", drawArray);

            JSONArray winnerArray = new JSONArray();
            JSONObject winner = new JSONObject();
            winner.put("SGL", infoArchive.getWinner()[0]);
            winner.put("DBL", infoArchive.getWinner()[1]);
            winnerArray.add(winner);
            tournamentObject.put("Winner", winnerArray);
            tournamentObject.put("Year", infoArchive.getYear());

            objList.add(tournamentObject);
        }

        Path tournamentInfoPath = Paths.get(TOURNAMENT_INFO_ARCHIVE_DIR);
        if(Files.notExists(tournamentInfoPath)) {
            File tournamentDir = new File(TOURNAMENT_INFO_ARCHIVE_DIR);
            try {
                tournamentDir.mkdir();
            } catch (SecurityException e) {
                logger.error("Unable to create directory " + e);
            }
        }

        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(TOURNAMENT_INFO_ARCHIVE_DIR+"/"+year+TOURNAMENT_INFO_ARCHIVE_FILE))) {
            for(JSONObject tournamentObject : objList) {
                writer.write(tournamentObject.toJSONString() + "\n");
            }
            objList.clear();
        } catch (IOException e) {
            logger.error("Unable to write to file " + e);
        }
    }
}
