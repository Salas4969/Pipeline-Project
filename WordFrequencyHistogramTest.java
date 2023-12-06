import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class WordFrequencyHistogramTest {

    @Test
    public void testReadTextFromFile() {
        // Arrange
        String filePath = "aawiki-20231001-pages-logging.xml.gz";

        // Act
        String text = WordFrequencyHistogram.readTextFromFile(filePath);

        // Assert
        assertNotNull(text);
        // You may add more specific assertions based on your test data
    }

    @Test
    public void testCountWordFrequency() {
        // Arrange
        String sampleText = "This is a sample text. Text is a sample.";

        // Act
        Map<String, Integer> wordFrequency = WordFrequencyHistogram.countWordFrequency(sampleText);

        // Assert
        assertNotNull(wordFrequency);
        assertEquals(2, wordFrequency.get("text"));
        assertEquals(2, wordFrequency.get("sample"));
        // ... add more assertions as needed
    }

    // Add more tests as needed

    @Test
    public void testDrawHistogram() {
        // Arrange
        Map<String, Integer> wordFrequency = new HashMap<>();
        wordFrequency.put("apple", 3);
        wordFrequency.put("banana", 5);

        // Act (Assuming drawHistogram outputs to console)
        // Uncomment the line below if you want to capture the console output for testing
        // ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        // System.setOut(new PrintStream(outContent));

        WordFrequencyHistogram.drawHistogram(wordFrequency);

        // Uncomment the lines below if you want to check the console output
        // String expectedOutput = "Expected output based on your data";
        // assertEquals(expectedOutput, outContent.toString().trim());

        // Reset console output stream
        // System.setOut(System.out);
    }
}
