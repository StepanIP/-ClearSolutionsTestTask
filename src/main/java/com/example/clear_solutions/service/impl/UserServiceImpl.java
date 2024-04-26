package com.example.clear_solutions.service.impl;

import com.example.clear_solutions.model.User;
import com.example.clear_solutions.repository.UserRepository;
import com.example.clear_solutions.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * UserServiceImpl is the implementation of the UserService interface.
 * It provides the business logic for creating, updating, deleting, and finding Users.
 * It uses a UserRepository to interact with the database.
 * It also checks that a User is at least a certain age before creating them.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * The minimum age a User must be to be created.
     * This value is injected from the application properties.
     */
    @Value("${user.min.age}")
    private int minAge;

    /**
     * The UserRepository used to interact with the database.
     */
    private final UserRepository userRepository;

    /**
     * Constructs a UserServiceImpl with the specified UserRepository.
     *
     * @param userRepository the UserRepository to be used
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new User if they are at least the minimum age.
     *
     * @param user the User to be created
     * @return the created User
     * @throws IllegalArgumentException if the User is not at least the minimum age
     */
    public User createUser(User user) {
        if (Period.between(user.getBirthDate(), LocalDate.now()).getYears() < minAge) {
            throw new IllegalArgumentException("User must be at least " + minAge + " years old");
        }
        return userRepository.save(user);
    }

    /**
     * Updates the specified fields of an existing User.
     *
     * @param id the ID of the User to be updated
     * @param userUpdates a User object containing the fields to be updated
     * @return the updated User
     * @throws IllegalArgumentException if the User is not found
     */
    public User updateUserFields(Long id, User userUpdates) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (userUpdates.getEmail() != null) user.setEmail(userUpdates.getEmail());
        if (userUpdates.getFirstName() != null) user.setFirstName(userUpdates.getFirstName());
        if (userUpdates.getLastName() != null) user.setLastName(userUpdates.getLastName());
        if (userUpdates.getBirthDate() != null) user.setBirthDate(userUpdates.getBirthDate());
        if (userUpdates.getAddress() != null) user.setAddress(userUpdates.getAddress());
        if (userUpdates.getPhoneNumber() != null) user.setPhoneNumber(userUpdates.getPhoneNumber());
        return userRepository.save(user);
    }

    /**
     * Updates all fields of an existing User.
     *
     * @param id the ID of the User to be updated
     * @param userUpdates a User object containing the updated fields
     * @return the updated User
     * @throws IllegalArgumentException if not all fields are updated or if the User is not found
     */
    public User updateAllUserFields(Long id, User userUpdates) {
        if (userUpdates.getEmail() == null || userUpdates.getFirstName() == null || userUpdates.getLastName() == null ||
            userUpdates.getBirthDate() == null || userUpdates.getAddress() == null || userUpdates.getPhoneNumber() == null) {
            throw new IllegalArgumentException("All fields must be updated");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setEmail(userUpdates.getEmail());
        user.setFirstName(userUpdates.getFirstName());
        user.setLastName(userUpdates.getLastName());
        user.setBirthDate(userUpdates.getBirthDate());
        user.setAddress(userUpdates.getAddress());
        user.setPhoneNumber(userUpdates.getPhoneNumber());

        return userRepository.save(user);
    }

    /**
     * Deletes a User.
     *
     * @param id the ID of the User to be deleted
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Finds Users whose birth date is within the specified range.
     *
     * @param from the start of the birth date range
     * @param to the end of the birth date range
     * @return a list of Users whose birth date is within the specified range
     * @throws IllegalArgumentException if the 'from' date is after the 'to' date
     */
    public List<User> findUsersByBirthDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("'From' date must be less than 'To' date");
        }
        return userRepository.findByBirthDateBetween(from, to);
    }
}