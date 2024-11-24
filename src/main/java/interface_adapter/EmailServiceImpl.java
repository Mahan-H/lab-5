package interface_adapter;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailServiceImpl implements EmailService {

    private final String username;
    private final String password;

    public EmailServiceImpl() {
        this.username = System.getenv("EMAIL_USERNAME");
        this.password = System.getenv("EMAIL_PASSWORD");

        if (username == null || password == null) {
            throw new IllegalStateException("Environment variables EMAIL_USERNAME and EMAIL_PASSWORD must be set.");
        }
    }

    @Override
    public void sendVerificationEmail(String sendingAddress, String subject, String body) {
        final Properties props = new Properties();
        props.put("mail.smtp.host", "smtp-relay.brevo.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        final Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            final Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendingAddress));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        }
        catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }
    }
}