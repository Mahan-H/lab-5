package use_case.verify;

/**
 * The input data for the Verify Email Use Case.
 */

public class VerifyInputData {
    private final String email;
    private final String verifyCode;

    /**
     * Constructs a new VerifyInputData instance with the specified email and verification code.
     *
     * @param email      the user's email address
     * @param verifyCode the verification code
     */
    public VerifyInputData(String email, String verifyCode) {
        this.email = email;
        this.verifyCode = verifyCode;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the verification code.
     *
     * @return the verification code
     */
    public String getVerifyCode() {
        return verifyCode;
    }
}