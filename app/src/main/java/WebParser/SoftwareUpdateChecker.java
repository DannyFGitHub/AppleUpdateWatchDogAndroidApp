package WebParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class SoftwareUpdateChecker {

    public SoftwareUpdateChecker() {

    }

    public static LinkedList<RecordOfSoftUpdate> getSoftUpdateRecords(){

        LinkedList<RecordOfSoftUpdate> recordsOfSoftUpdates = new LinkedList<>();

        Document doc = null;
        try {
            doc = Jsoup.connect("https://support.apple.com/en-au/HT201222").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //Get all tables that are called tableWrapper
            Element tableWrapper = doc.getElementById("tableWraper");

            //Then get the table body by:
            Elements tableBody = tableWrapper.getElementsByTag("tbody");

            Elements tableRows = tableBody.get(0).getElementsByTag("tr");

            for (Element tableRow : tableRows) {

                //If there is a heading don't count it as a table row
                if (!tableRow.getElementsByTag("th").isEmpty()){
                    continue;
                }

                try {

                    String urlLink = tableRow.select("td").select("a").attr("href");

                    URL url = null;
                    try {
                        url = new URL(urlLink);
                    } catch (MalformedURLException e) {
                        //e.printStackTrace();
                    }

                    ArrayList<String> rowDataText = (ArrayList<String>) tableRow.select("td").eachText();

                    String name = rowDataText.get(0);
                    String deviceAvailability = rowDataText.get(1);
                    String releaseDate = rowDataText.get(2);

                    recordsOfSoftUpdates.add(new RecordOfSoftUpdate(name, url, deviceAvailability, releaseDate));
                } catch (Exception ex){
                    ex.printStackTrace();
                    System.out.println(tableRow.text());
                }
            }

            System.out.println(tableBody.select("tr").size());

        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }

        System.out.println(recordsOfSoftUpdates.size());
        return recordsOfSoftUpdates;
    }


}

