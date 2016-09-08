package com.abhidesikan.tennisstatscrawler;

import com.abhidesikan.mongodb.DBConnector;
import com.abhidesikan.tennistippingpredictor.OddsExtractor;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.Doc;
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
            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
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

    public static void testMongoDb() {
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
        MongoDatabase db = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("testColl");
        System.out.println(collection +"collection");
        Document document =  new Document();
        document.put("_id", 1);
        document.put("Name", "Abhishek");
        document.put("Phone", "7165989291");
        collection.insertOne(document);
    }

    public static void main(String args[]) throws Exception{
       // testJSoup("http://www.atpworldtour.com/en/scores/results-archive?year=2016");
        TournamentInfoExtractor infoExtractor = new TournamentInfoExtractor();
        OddsExtractor oddsExtractor = new OddsExtractor();
//        infoExtractor.getTournamentInformationForYear("2011");
  //      infoExtractor.getTournamentInformationForAllYears();
 //       testMongoDb();
        DBConnector dbConnector = new DBConnector();
        dbConnector.connectToDatabase("ATP_Database");
        MongoCollection collection = dbConnector.getCollection("ResultsArchive");
        dbConnector.generateDocumentForAllYearsInCollection(collection);

    }
}
