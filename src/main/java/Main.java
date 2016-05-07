import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by abhidesikan on 5/6/16.
 */
public class Main {


    public static void testJSoup(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("#mp-itn b a");
            System.out.println(elements.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        testJSoup("http://en.wikipedia.org/");
    }
}
