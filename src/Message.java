import java.util.Random;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Message {
     String messageID;
     int numSent;
     String recipient;
     String message;
     String sender = "You";
     String flag = "";
     public static ArrayList<Message> sentMessages = new ArrayList<>();
     public static ArrayList<Message> storedMessages = new ArrayList<>();
     public static ArrayList<String> messageHashes = new ArrayList<>();
     public static ArrayList<String> messageIDs = new ArrayList<>();
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
        this.setFlag("Sent");
        Message.sentMessages.add(this);
        allMessages.add(this);
        totalSent++;
        storeMessage();
        return "Message successfully sent.";
    } else if (option.equalsIgnoreCase("Disregard")) {
        return "Press 0 to delete the message.";
    } else if (option.equalsIgnoreCase("Store")) {
        this.setFlag("Stored");
        Message.allMessages.add(this);
        Message.storedMessages.add(this);
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
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getSender() { return sender; }
    public String getFlag() { return flag; }
    public void setSender(String s) { this.sender = s; }
    public void setFlag(String f) { this.flag = f; }

    // --- PART 3 ---
    public static void populateTestData() {
        allMessages.clear(); sentMessages.clear(); storedMessages.clear();
        messageHashes.clear(); messageIDs.clear(); totalSent = 0;
        Message m1 = new Message("+27834557896", "Did you get the cake?", 0);
        m1.setSender("0838884567"); m1.SentMessage("Send");
        Message m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 1);
        m2.setSender("0838884567"); m2.SentMessage("Store");
        Message m3 = new Message("+27834484567", "Yohoooo, I am at your gate.", 2);
        m3.setSender("0838884567"); m3.SentMessage("Disregard");
        Message m4 = new Message("0838884567", "It is dinner time!", 3);
        m4.setSender("0838884567"); m4.SentMessage("Send");
        Message m5 = new Message("+27838884567", "Ok, I am leaving without you.", 4);
        m5.setSender("0838884567"); m5.SentMessage("Store");
}

    public static String displaySenderAndRecipient() {
        String out = "";
        for (Message m : storedMessages) out += "Sender: " + m.sender + " | Recipient: " + m.recipient + "\n";
        return out;
}
    public static String getLongestMessage() {
        String longest = "";
        for (Message m : storedMessages) if (m.message.length() > longest.length()) longest = m.message;
        return longest;
}
    public static String searchByMessageID(String id) {
        for (Message m : allMessages) if (m.messageID.equals(id)) return "Recipient: " + m.recipient + "\nMessage: " + m.message;
        return "Message ID not found.";
}
    public static String searchByRecipient(String rec) {
        String out = "";
        for (Message m : allMessages) if (m.recipient.equals(rec)) out += m.message + "\n";
        return out.isEmpty() ? "No messages found." : out;
}
    public static String deleteByHash(String hash) {
        for (int i = 0; i < allMessages.size(); i++) {
        Message m = allMessages.get(i);
        if (m.createMessageHash().equalsIgnoreCase(hash)) {
            String txt = m.message;
            allMessages.remove(i); storedMessages.remove(m); sentMessages.remove(m);
            return "Message: \"" + txt + "\" successfully deleted.";
        }
    }
    return "Hash not found.";
}
    public static String displayReport() {
        String out = "--- STORED + SENT REPORT ---\n";
        for (Message m : allMessages) out += "Hash: " + m.createMessageHash() + "\nRecipient: " + m.recipient + "\nMessage: " + m.message + "\n\n";
        return out;
}
}
