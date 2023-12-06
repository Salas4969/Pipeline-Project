import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordFrequencyHistogram {

    public static void main(String[] args) {
        // Step 1: Read the text from a file
        String filePath = "aawiki-20231001-pages-logging.xml.gz";
        String text = readTextFromFile(filePath);

        // Step 2: Count word frequency
        Map<String, Integer> wordFrequency = countWordFrequency(text);

        // Step 3: Display word counts
        System.out.println("Word Counts:");
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Step 4: Draw histogram
        drawHistogram(wordFrequency);
    }

    private static String readTextFromFile(String filePath) {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    private static Map<String, Integer> countWordFrequency(String text) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        String[] words = text.split("\\s+");

        for (String word : words) {
            // Remove non-alphabetic characters and convert to lowercase
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

            // Update word frequency
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }

        return wordFrequency;
    }

    private static void drawHistogram(Map<String, Integer> wordFrequency) {
        System.out.println("\nWord Frequency Histogram:");

        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            String word = entry.getKey();
            int frequency = entry.getValue();

            System.out.print(word + ": ");
            for (int i = 0; i < frequency; i++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
