package com.example.clear_solutions.service;

import com.example.clear_solutions.model.User;
import com.example.clear_solutions.repository.UserRepository;
import com.example.clear_solutions.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * UserServiceImplTest is a test class for UserServiceImpl.
 * It uses Spring's ReflectionTestUtils to set private fields.
 * It also uses Mockito to mock the UserRepository.
 */
@SpringBootTest
public class UserServiceImplTest {

    /**
     * The UserServiceImpl instance to be tested.
     * This instance is automatically injected with mocks by Mockito.
     */
    @InjectMocks
    UserServiceImpl userService;

    /**
     * The mocked UserRepository.
     */
    @Mock
    UserRepository userRepository;

    /**
     * Tests that creating a user with a valid age returns the created user.
     */
    @Test
    @DisplayName("Creating a user with valid age returns the created user")
    public void createUserWithValidAgeReturnsUser() {
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(20));
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertEquals(user, createdUser);
    }

    /**
     * Tests that creating a user with an invalid age throws an IllegalArgumentException.
     */
    @Test
    @DisplayName("Creating a user with invalid age throws IllegalArgumentException")
    public void createUserWithInvalidAgeThrowsException() {
        ReflectionTestUtils.setField(userService, "minAge", 18);
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(10));

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
    }

    /**
     * Tests that updating user fields returns the updated user.
     */
    @Test
    @DisplayName("Updating user fields returns the updated user")
    public void updateUserFieldsReturnsUpdatedUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUserFields(1L, user);

        assertEquals(user, updatedUser);
    }

    /**
     * Tests that updating all user fields returns the updated user.
     */
    @Test
    @DisplayName("Updating all user fields returns the updated user")
    public void updateAllUserFieldsReturnsUpdatedUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("testemail@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setAddress("Test Address");
        user.setPhoneNumber("1234567890");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateAllUserFields(1L, user);

        assertEquals(user, updatedUser);
    }

    /**
     * Tests that updating all user fields with missing fields throws an IllegalArgumentException.
     */
    @Test
    @DisplayName("Updating all user fields with missing fields throws IllegalArgumentException")
    public void updateAllUserFieldsWithMissingFieldsThrowsException() {
        User userUpdates = new User();
        userUpdates.setId(1L);
        userUpdates.setEmail("newemail@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> userService.updateAllUserFields(1L, userUpdates));
    }

    /**
     * Tests that deleting a user does not throw an exception.
     */
    @Test
    @DisplayName("Deleting a user does not throw an exception")
    public void deleteUserDoesNotThrowException() {
        userService.deleteUser(1L);
    }

    /**
     * Tests that finding users by a valid birth date range does not throw an exception.
     */
    @Test
    @DisplayName("Finding users by valid birth date range does not throw an exception")
    public void findUsersByValidBirthDateRangeDoesNotThrowException() {
        LocalDate from = LocalDate.now().minusDays(1);
        LocalDate to = LocalDate.now();

        userService.findUsersByBirthDateRange(from, to);
    }

    /**
     * Tests that finding users by an invalid birth date range throws an IllegalArgumentException.
     */
    @Test
    @DisplayName("Finding users by invalid birth date range throws IllegalArgumentException")
    public void findUsersByInvalidBirthDateRangeThrowsException() {
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().minusDays(1);

        assertThrows(IllegalArgumentException.class, () -> userService.findUsersByBirthDateRange(from, to));
    }
}