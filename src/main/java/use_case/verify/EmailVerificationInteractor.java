package use_case.verify;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import interface_adapter.EmailService;

public class EmailVerificationInteractor {
    private final EmailService emailService;
    private final Map<String, String> verificationCodes = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public EmailVerificationInteractor(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendVerificationEmail(String email) {
        String verificationCode = generateVerificationCode(6);

        verificationCodes.put(email, verificationCode);

        // Send the verification email
        String subject = "Your Verification Code";
        String message = "Your verification code is: " + verificationCode;
        emailService.sendVerificationEmail(email, subject, message);
    }

    public boolean verifyCode(String email, String code) {
        String storedCode = verificationCodes.get(email);
        if (storedCode != null && storedCode.equals(code)) {
            verificationCodes.remove(email);
            return true;
        }
        return false;
    }

    private String generateVerificationCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }
}
