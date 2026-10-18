import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testCheckMessageID() {
        Message m = new Message("+27717265217", "Hi Mimi", 0);
        assertTrue(m.checkMessageID());
        assertEquals(10, m.getMessageID().length());
    }

    @Test
    public void testRecipientSuccess() {
        Message m = new Message("+27717265217", "Hello", 0);
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    @Test
    public void testRecipientFail() {
        Message m = new Message("07717265217", "Hello", 0);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", m.checkRecipientCell());
    }

    @Test
    public void testMessageLengthSuccess() {
        Message m = new Message("+27717265217", "i love you mimi", 0);
        assertEquals("Message ready to send.", m.checkMessageLength());
    }

    @Test
    public void testMessageLengthFail() {
        String longMsg = "a".repeat(300);
        Message m = new Message("+27717265217", longMsg, 0);
        assertTrue(m.checkMessageLength().contains("exceeds 250"));
    }

    @Test
    public void testCreateMessageHash() {
        Message m = new Message("+27717265217", "Hi Mimi", 0);
        String hash = m.createMessageHash();
        
        assertTrue(hash.contains(":0:"));
        assertTrue(hash.contains("HI"));
    }

    @Test
    public void testSentMessageSend() {
        Message m = new Message("+27717265217", "Test", 0);
        assertEquals("Message successfully sent.", m.SentMessage("Send"));
    }

    @Test
    public void testSentMessageStore() {
        Message m = new Message("+27717265217", "Test", 0);
        assertEquals("Message successfully stored.", m.SentMessage("Store"));
    }

    @Test
    public void testSentMessageDisregard() {
        Message m = new Message("+27717265217", "Test", 0);
        assertEquals("Press 0 to delete the message.", m.SentMessage("Disregard"));
    }
}
