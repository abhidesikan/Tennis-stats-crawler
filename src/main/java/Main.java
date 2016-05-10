import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by abhidesikan on 5/6/16.
 */
public class Main {


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
                    for(Element th: tr.select("th")) {
                        System.out.println(th.text());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        testJSoup("http://www.atpworldtour.com/en/scores/results-archive?year=2016");
    }
}
