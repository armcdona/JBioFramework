package utilities;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.swing.*;

/**
 *
 */

public class EmailClient {

    //current JBF email
    static String jbfEmail = "JBioFramework@gmail.com";

    //set properties
    static String defaultServer = "localhost";

    /** send test message */
    public static void main(String[] args){

        //get default message object
        Message testMessage = getBlankMessage();

        try{

            //set sender email
            testMessage.setFrom( new InternetAddress(jbfEmail) );

            //set recipient email
            testMessage.addRecipient( Message.RecipientType.TO,
                                        new InternetAddress(jbfEmail));

            //set subject
            testMessage.setSubject("EmailClient test");

            //set test
            testMessage.setText("This is an automated message to test " +
                    "EmailClient. \n Test Successful!");

            Transport.send(testMessage);


        }catch(Exception exception){

            //make popup for the user

            String taBody = "There was an error sending your message. \n\n" +
                    "Please return to the previous screen, open your preferred " +
                    "email client and send the message to: " + jbfEmail;

            JOptionPane.showMessageDialog(null,taBody);

            //print exception stack trace for programmer
            exception.printStackTrace();
        }

    }

    /**
     * test EmailClient by sending defined message
     */
    public void sendDirect(String senderEmail, String subject, String messageBody){

        //get default message object
        Message testMessage = getBlankMessage();

        try{

            //set sender email
            testMessage.setFrom( new InternetAddress(jbfEmail) );

            //set recipient email
            testMessage.addRecipient( Message.RecipientType.TO,
                                        new InternetAddress(jbfEmail));

            //set subject
            testMessage.setSubject("EmailClient test");

            //set test
            testMessage.setText("This is an automated message to test " +
                                    "EmailClient. \n Test Successful!");

            Transport.send(testMessage);

        }catch(Exception exception){
            System.out.println("Error sending message");
            exception.printStackTrace();
        }


    }

    /**
     * prevents code duplication by creating a blank message object with
     *   set Session and Property objects
     * @return
     */
    public static Message getBlankMessage(){

        //set properties
        Properties properties = System.getProperties();
        properties.setProperty("mail.stmp.host",defaultServer);

        //get default session object
        Session session = Session.getDefaultInstance(properties);

        MimeMessage message = new MimeMessage(session);

        return message;
    }

}
