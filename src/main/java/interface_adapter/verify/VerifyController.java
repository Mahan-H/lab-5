package interface_adapter.verify;

import use_case.verify.VerifyInputBoundary;
import use_case.verify.VerifyInputData;

/**
 * Controller for the verify Use Case.
 */

public class VerifyController {
    private final VerifyInputBoundary verifyUseCaseInteractor;

    public VerifyController(VerifyInputBoundary verifyUseCaseInteractor) {
        this.verifyUseCaseInteractor = verifyUseCaseInteractor;
    }

    /**
     * Executes the Verify Email Use Case.
     * @param email the email of the user whose password to change
     * @param verifyCode the verification code that is being sent to the user.
     */

    public void execute(String email, String verifyCode) {
        final VerifyInputData verifyInputData = new VerifyInputData(email, verifyCode);
        verifyUseCaseInteractor.execute(verifyInputData);
    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */

    public void switchToSignUpView() {
        verifyUseCaseInteractor.switchToSignUpView();
    }

    /**
     * Resends the verification email to the user.
     * @param email the user's email
     */
    public void resendVerificationEmail(String email) {
        VerifyInputData inputData = new VerifyInputData(email, null);
        verifyUseCaseInteractor.resendVerificationEmail(inputData);
    }

}
