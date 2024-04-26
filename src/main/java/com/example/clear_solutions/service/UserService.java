package com.example.clear_solutions.service;

import com.example.clear_solutions.model.User;
import java.time.LocalDate;
import java.util.List;

/**
 * UserService is an interface that defines the contract for the User service.
 * It declares methods for creating, updating, deleting, and finding Users.
 * The methods are expected to be implemented by a class that provides the actual business logic.
 */
public interface UserService {

    /**
     * Creates a new User.
     *
     * @param user the User to be created
     * @return the created User
     */
    User createUser(User user);

    /**
     * Updates the specified fields of an existing User.
     *
     * @param id the ID of the User to be updated
     * @param userUpdates a User object containing the fields to be updated
     * @return the updated User
     */
    User updateUserFields(Long id, User userUpdates);

    /**
     * Updates all fields of an existing User.
     *
     * @param id the ID of the User to be updated
     * @param userUpdates a User object containing the updated fields
     * @return the updated User
     */
    User updateAllUserFields(Long id, User userUpdates);

    /**
     * Deletes a User.
     *
     * @param id the ID of the User to be deleted
     */
    void deleteUser(Long id);

    /**
     * Finds Users whose birthdate is within the specified range.
     *
     * @param from the start of the birthdate range
     * @param to the end of the birthdate range
     * @return a list of Users whose birthdate is within the specified range
     */
    List<User> findUsersByBirthDateRange(LocalDate from, LocalDate to);
}