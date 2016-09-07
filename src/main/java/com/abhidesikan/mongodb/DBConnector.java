package com.abhidesikan.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by abhidesikan on 8/24/16.
 */
public class DBConnector {

    private static final String HOST_NAME = "localhost";
    private static int PORT = 27017;
    private static final String RESULTS_ARCHIVE_COLLECTION = "ResultsArchiveCollection";

    private  MongoClient mongoClient = null;
    private  MongoCollection<Document> collection = null;
    private  MongoDatabase db = null;

    final Logger logger = LoggerFactory.getLogger(DBConnector.class);


    public void connectToDatabase(String databaseName) {
        try {
            mongoClient = new MongoClient(new ServerAddress(HOST_NAME, PORT));
        } catch (Exception e) {
            logger.error("Unable to connect :"+e);
        }
        db = mongoClient.getDatabase(databaseName);
        logger.info("Connection to " + databaseName + " established succesfully");
    }

    public void storeCollectionToDatabase(String collectionName) throws Exception {
        switch (collectionName) {
            case "ResultsArchive":
                logger.info("Connecting to collection " + collectionName);
                collection = db.getCollection(RESULTS_ARCHIVE_COLLECTION);
                break;
            default:
                logger.error("Invalid collection name. Choose from - ResultsArchive, TournamentArchive, Head2Head or MatchStatsArchive");
        }

        String tournamentFile = "ResultsArchiveFiles/1915_tournament_results.json";
        List objList = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(tournamentFile))) {
            objList = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Unable to read from file " + e);
        }

        Document resultsArchiveDocument;
        for(Object object : objList) {
            String test = (String) object;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(test);
            resultsArchiveDocument = Document.parse(jsonObject.toJSONString());
            collection.insertOne(resultsArchiveDocument);
        }
    }
}
