package use_case.signup;

import entity.User;
import entity.UserFactory;
import interface_adapter.EmailServiceImpl;

import java.security.SecureRandom;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;
    private final EmailServiceImpl emailService;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory,
                            EmailServiceImpl emailService) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
        this.emailService = emailService;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByName(signupInputData.getEmail())) {
            userPresenter.prepareFailView("User already exists.");
        }
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else {
            User user = userFactory.create(signupInputData.getEmail(), signupInputData.getPassword());
            userDataAccessObject.save(user);

            // Generate a verification code
            String verificationCode = generateVerificationCode(6);
            // Send verification email
            emailService.sendVerificationEmail(user.getEmail(), "Osiris Verification Code", verificationCode);

            SignupOutputData signupOutputData = new SignupOutputData(user.getEmail(), false);
            userPresenter.prepareSuccessView(signupOutputData);
        }
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

    @Override
    public void switchToVerifyView() {
        userPresenter.switchToVerifyView();
    }

    @Override
    public void switchToWelcomeView() {
        userPresenter.switchToWelcomeView();
    }
}
