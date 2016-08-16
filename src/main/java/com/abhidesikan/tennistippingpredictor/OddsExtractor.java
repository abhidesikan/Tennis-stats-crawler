package com.abhidesikan.tennistippingpredictor;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by abhidesikan on 7/28/16.
 */
public class OddsExtractor {


    final Logger logger = LoggerFactory.getLogger(OddsExtractor.class);

    public void getOddsFromUrl(String url) {

        try{
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Elements elements = doc.select(".inner-content");
            System.out.println(elements.text());
//            for(Element element: elements) {
//                System.out.println(element.text());
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getElements() throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        final HtmlPage page = webClient.getPage("http://www.oddschecker.com/tennis/mens-rogers-cup/kevin-anderson-v-stan-wawrinka/winner");
        final HtmlDivision div = page.getHtmlElementById("my_chart");
        System.out.println(div.asText());

    }


}
