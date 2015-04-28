/**
 * Sends email from within application through GMail
 *
 * Code written by 'Bill the Lizard' on StackOverflow
 *   [http://stackoverflow.com/users/1288/bill-the-lizard],
 *   post: http://stackoverflow.com/questions/46663/how-to-send-an-email-by-java-application-using-gmail-yahoo-hotmail
 *
 * Edited and commented by aks5238
 *
 * note: sends from gmail addresses only
 */

package utilities;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;

public class GMailClient {

    //set static sender/receiver info for testing
    private static String jbfEmail  = "JBioFramework@gmail.com";
    private static String USER_NAME = "JBioFramework";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "proteomics"; // GMail password
    private static String RECIPIENT = "JBioFramework";

    /**
     * Send test message from static variables
     */
    public static void main(String[] args) {

        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = {RECIPIENT}; // list of recipient email addresses
        String subject = "Java send mail example";
        String body = "Main.Welcome to JavaMail!";

        try {
            sendFromGMail(from, pass, to, subject, body);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendFromGMail(String from, String pass, String[] to, String subject, String body) throws Exception{

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            //set message subject, body, then send
            //try {
            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            //}catch(Exception e){
            //    JOptionPane.showMessageDialog(null,"Message failed to send.", null, JOptionPane.CANCEL_OPTION);
            //}

            //notify user that email was sent correctly
            JOptionPane.showMessageDialog(null,"Message sent!", null, JOptionPane.INFORMATION_MESSAGE);


        }
        catch (AddressException ae) {
            ae.printStackTrace();
            throw ae;
        }
        catch (MessagingException me) {
            me.printStackTrace();
            throw me;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }
}
