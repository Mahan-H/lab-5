package osiris.use_case.signup;

import osiris.entity.User;

/**
 * DAO for the Signup Use Case.
 */
public interface SignupUserDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param email the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String email);

    /**
     * Saves the user.
     * @param user the user to save
     */
    void save(User user);
}
