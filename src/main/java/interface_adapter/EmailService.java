package interface_adapter;

public interface EmailService {

    void sendVerificationEmail(String sendingAddress, String subject, String body);
}
