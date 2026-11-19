import java.util.Scanner;

public class QuickChatApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to QuickChat.");

        System.out.print("How many messages do you wish to enter? ");
        int numMessages = Integer.parseInt(sc.nextLine());
        // Use Nested loop ("if" inside "for" loop)
        int sentCount = 0;
        for (int i = 0; i < numMessages; ) {
            System.out.println("\n1) Send Messages\n2) Show recently sent messages\n3) Quit\n4 Stored Messages");
            System.out.print("Select: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 3) {
                System.out.println("Total messages sent: " + sentCount);
                break;
            }
            if (choice == 2) { System.out.println("Coming Soon."); continue; }
            
            if (choice == 4) {
                System.out.println("a) Sender/Recipient\nb) Longest\nc) Search by ID\nd) Search by Recipient\ne) Delete by Hash\nf) Report");
            String opt = sc.nextLine();
            if (opt.equalsIgnoreCase("a")) {
                System.out.println(Message.displaySenderAndRecipient()); }
            if (opt.equalsIgnoreCase("b")) {
               System.out.println(Message.getLongestMessage()); }
            if (opt.equalsIgnoreCase("c")) { 
                System.out.print("Enter ID: "); System.out.println(Message.searchByMessageID(sc.nextLine())); }
            if (opt.equalsIgnoreCase("d")) { 
                System.out.print("Enter Recipient: "); 
                System.out.println(Message.searchByRecipient(sc.nextLine())); }
            if (opt.equalsIgnoreCase("e")) { 
                System.out.print("Enter Hash: "); 
                System.out.println(Message.deleteByHash(sc.nextLine())); }
            if (opt.equalsIgnoreCase("f")) {
                System.out.println(Message.displayReport()); }
            continue;
}

            System.out.print("Enter recipient (+277...): ");
            String rec = sc.nextLine();
            System.out.print("Enter message: ");
            String msg = sc.nextLine();

            Message m = new Message(rec, msg, sentCount);

            String lenCheck = m.checkMessageLength();
            System.out.println(lenCheck);
            if (lenCheck.contains("exceeds")) continue;

            String recCheck = m.checkRecipientCell();
            System.out.println(recCheck);
            if (recCheck.contains("incorrectly")) continue;

            System.out.println(m.checkMessageIDGenerated());
            System.out.println("Message Hash: " + m.createMessageHash());

            System.out.print("1-Send 2-Disregard 3-Store: ");
            String opt = sc.nextLine();
            String optText = opt.equals("1") ? "Send" : opt.equals("2") ? "Disregard" : "Store";
            System.out.println(m.SentMessage(optText));

            if (optText.equals("Send")) {
                sentCount++;
                i++;
            }
        }
        sc.close();
    }
}
