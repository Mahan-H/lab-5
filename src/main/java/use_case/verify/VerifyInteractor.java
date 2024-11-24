package use_case.verify;

import java.security.SecureRandom;

import data_access.DBUserDataAccessObject;
import interface_adapter.EmailService;

/**
 * The Verify Email Interactor.
 */

public class VerifyInteractor implements VerifyInputBoundary {
    private final VerifyOutputBoundary userPresenter;
    private final EmailService emailService;
    private final DBUserDataAccessObject dataAccessObject;

    public VerifyInteractor(VerifyOutputBoundary verifyOutputBoundary, EmailService emailService, DBUserDataAccessObject dataAccessObject) {
        this.userPresenter = verifyOutputBoundary;
        this.emailService = emailService;
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public void execute(VerifyInputData verifyInputData) {
        String email = verifyInputData.getEmail();
        String inputCode = verifyInputData.getVerifyCode();
        String storedCode = dataAccessObject.getVerificationCode(email);

        if (storedCode != null && storedCode.equals(inputCode)) {
            // Verification successful
            userPresenter.prepareSuccessView(new VerifyOutputData(email, false));
        }
        else {
            // Verification failed
            userPresenter.prepareFailView("Invalid verification code.");
        }
    }

    @Override
    public void switchToSignUpView() {
        userPresenter.switchToSignUpView();
    }

    @Override
    public void resendVerificationEmail(VerifyInputData inputData) {
        String email = inputData.getEmail();
        String newVerificationCode = generateVerificationCode(6);

        dataAccessObject.saveVerificationCode(email, newVerificationCode);

        emailService.sendVerificationEmail(email, "Resend Email Verification", "Your new verification code is: " + newVerificationCode);
    }

    private String generateVerificationCode(int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final SecureRandom random = new SecureRandom();
        final StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            final int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }
}
