package use_case.verify;

/**
 * The Verify Email Use Case.
 */
public interface VerifyInputBoundary {
    /**
     * Execute the Verify Email Use Case.
     * @param verifyInputData the input data for this use case
     */

    void execute(VerifyInputData verifyInputData);

    void switchToSignUpView();

    void resendVerificationEmail(VerifyInputData verifyInputData);

}
