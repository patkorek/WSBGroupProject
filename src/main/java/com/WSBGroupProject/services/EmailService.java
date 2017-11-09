package com.WSBGroupProject.services;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import com.WSBGroupProject.model.Account;
/**
 * send email via gmail account, ssl connection
 *
 * @author Piotr Czapiewski
 */
@Service
public class EmailService {

    final static private String MARKIET_URL = "http://beta.markiet.pl";
    final static private String EMAIL_HOST = "smtp.gmail.com";//"s55.linuxpl.com";
    final static private String EMAIL_FROM = "testzyrafy@gmail.com";//"noreply@markiet.pl";
    final static private String EMAIL_PASSWORD = "zyrafyzszafy";//"4BABDF51";
    final static private String RESET_EMAIL = "Markiet - reset hasła";
    final static private String SIGNUP_EMAIL = "Witamy w Markiet";
    
    private void sendEmail(String recipient, String subject, String messageBody){
        Properties props = new Properties();
        props.put("mail.smtp.host", EMAIL_HOST);
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_FROM,EMAIL_PASSWORD);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void resetPassword(Account a, String newPassword){
        String message = "Witaj " + a.getFirstName() + " " + a.getLastName() + ",\n"
                + "Twoje nowe hasło to:\n"
                + newPassword + "\n\n"
                + "Pozdrawiamy\n"
                + "Zespół Markiet";
        sendEmail(a.getUsername(), RESET_EMAIL, message);
    }
    
    public void signUp(Account a){
        String message = "Witaj " + a.getFirstName() + " " + a.getLastName() + ",\n"
                + "Twoje konto zostało utworzone - aktywuj je klikając w poniższy link.\n"
                + MARKIET_URL + "/activate?key=" + a.getHashLink() + "\n\n"
                + "Pozdrawiamy\n"
                + "Zespół Markiet";
        sendEmail(a.getUsername(), SIGNUP_EMAIL, message);
    }

    public String getTestEmail() {
        return EMAIL_FROM;
    }
}