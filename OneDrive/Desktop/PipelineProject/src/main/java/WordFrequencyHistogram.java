import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WordFrequencyHistogram {

    public static void main(String[] args) {
        System.out.println("Current Directory: " + System.getProperty("user.dir"));

        // Step 1: Parse the XML file
        String filePath = "WikidataTitles.xml";
        Document document = parseXML(filePath);

        // Step 2: Extract log titles
        Map<String, Integer> logTitleFrequency = extractLogTitles(document);

        // Step 3: Display log title counts
        System.out.println("Log Title Counts:");
        for (Map.Entry<String, Integer> entry : logTitleFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Step 4: Draw histogram
        drawHistogram(logTitleFrequency);

        // Step 5: Fit to power-law
        fitToPowerLaw(logTitleFrequency);
    }

    private static Document parseXML(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, Integer> extractLogTitles(Document document) {
        Map<String, Integer> logTitleFrequency = new HashMap<>();

        // Get all <logitem> elements
        NodeList logItemNodes = document.getElementsByTagName("logitem");

        // Iterate over <logitem> elements
        for (int i = 0; i < logItemNodes.getLength(); i++) {
            Element logItemElement = (Element) logItemNodes.item(i);

            // Get the content within <logtitle>
            NodeList logTitleNodes = logItemElement.getElementsByTagName("logtitle");
            if (logTitleNodes.getLength() > 0) {
                String logTitle = logTitleNodes.item(0).getTextContent();

                // Update log title frequency
                logTitleFrequency.put(logTitle, logTitleFrequency.getOrDefault(logTitle, 0) + 1);
            }
        }

        return logTitleFrequency;
    }

    private static void drawHistogram(Map<String, Integer> logTitleFrequency) {
        System.out.println("\nLog Title Frequency Histogram:");

        int maxFrequency = logTitleFrequency.values().stream().mapToInt(Integer::intValue).max().orElse(0);

        for (Map.Entry<String, Integer> entry : logTitleFrequency.entrySet()) {
            String logTitle = entry.getKey();
            int frequency = entry.getValue();

            // Normalize frequency to a scale of 10
            int scaledFrequency = (int) Math.ceil((double) frequency / maxFrequency * 10);

            System.out.printf("%-20s | %s%n", logTitle, "*".repeat(scaledFrequency));
        }
    }

    private static void fitToPowerLaw(Map<String, Integer> logTitleFrequency) {
        System.out.println("\nFitting to Power Law:");

        SimpleRegression regression = new SimpleRegression();

        // Loop through data points and add them to the regression
        for (Map.Entry<String, Integer> entry : logTitleFrequency.entrySet()) {
            double logCount = Math.log(logTitleFrequency.get(entry.getKey()));
            double logFrequency = Math.log(entry.getValue());
            regression.addData(logCount, logFrequency);
        }

        double alpha = regression.getSlope();
        double beta = regression.getIntercept();

        System.out.println("Power-law parameters: alpha = " + alpha + ", beta = " + beta);
    }
}
