import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Before
    public void setUp() {
        Message.populateTestData();
    }

    @Test
    public void testPopulateAndSenderRecipient() {
        String result = Message.displaySenderAndRecipient();
        assertTrue(result.contains("+27838884567"));
        assertTrue(result.contains("0838884567"));
    }

    @Test
    public void testLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.", Message.getLongestMessage());
    }

    @Test
    public void testSearchByMessageID() {
        String id = Message.allMessages.get(0).messageID;
        String result = Message.searchByMessageID(id);
        assertTrue(result.contains("Did you get the cake?"));
    }

    @Test
    public void testSearchByRecipient() {
        String result = Message.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late!"));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteByHash() {
        String hash = Message.allMessages.get(1).createMessageHash();
        String result = Message.deleteByHash(hash);
        assertTrue(result.contains("successfully deleted"));
    }

    @Test
    public void testDisplayReport() {
        String report = Message.displayReport();
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time!"));
    }
}