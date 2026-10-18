import java.util.Random;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Message {
     String messageID;
     int numSent;
     String recipient;
     String message;
    public static List<Message> allMessages = new ArrayList<>();
    public static int totalSent = 0;

    public Message(String recipient, String message, int numSent) {
        this.recipient = recipient;
        this.message = message;
        this.numSent = numSent;
        this.messageID = generateID();
    }

    public String generateID() {
        Random r = new Random();
        long num = 1000000000L + (long)(r.nextDouble() * 9000000000L);
        return String.valueOf(num);
    }

    public boolean checkMessageID() {
        return messageID.length() == 10;
    }
   
    public String checkMessageIDGenerated() {
        return "Message ID generated: " + messageID;
    }
    // Check Cellphone Number
    public String checkRecipientCell() {
        if (recipient != null && recipient.startsWith("+") && recipient.length() <= 13) {
            if (recipient.substring(1).matches("\\d+")) {
                return "Cell phone number successfully captured.";
            }
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
    // Check Message Length
    public String checkMessageLength() {
        if (message.length() > 250) {
            int x = message.length() - 250;
            return "Message exceeds 250 characters by " + x + ", please reduce the size.";
        } else {
            return "Message ready to send.";
        }
    }

    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String first = words[0].replaceAll("[^a-zA-Z]", "");
        String last = words[words.length-1].replaceAll("[^a-zA-Z]", "");
        String firstTwo = messageID.substring(0,2);
        return (firstTwo + ":" + numSent + ":" + first + last).toUpperCase();
    }

    public String SentMessage(String option) {
        if (option.equalsIgnoreCase("Send")) {
            allMessages.add(this);
            totalSent++;
            storeMessage();
            return "Message successfully sent.";
        } else if (option.equalsIgnoreCase("Disregard")) {
            return "Press 0 to delete the message.";
        } else if (option.equalsIgnoreCase("Store")) {
            storeMessage();
            return "Message successfully stored.";
        }
        return "Invalid";
    }

    public void storeMessage() {
        try {
            FileWriter fw = new FileWriter("messages.json", true);
            fw.write("{\"MessageID\":\"" + messageID + "\",\"Hash\":\"" + createMessageHash() + "\",\"Recipient\":\"" + recipient + "\",\"Message\":\"" + message + "\"}\n");
            fw.close();
        } catch (Exception e) {}
    }

    public int returnTotalMessages() { return totalSent; }
    public String getMessageID() { return messageID; }
}
