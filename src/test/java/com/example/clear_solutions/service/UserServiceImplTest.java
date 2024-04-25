package com.example.clear_solutions.service;

import com.example.clear_solutions.model.User;
import com.example.clear_solutions.repository.UserRepository;
import com.example.clear_solutions.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Creating a user with valid age returns the created user")
    public void createUserWithValidAgeReturnsUser() {
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(20));
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertEquals(user, createdUser);
    }

    @Test
    @DisplayName("Creating a user with invalid age throws IllegalArgumentException")
    public void createUserWithInvalidAgeThrowsException() {
        ReflectionTestUtils.setField(userService, "minAge", 18);
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(10));

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
    }

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

    @Test
    @DisplayName("Updating all user fields returns the updated user")
    public void updateAllUserFieldsReturnsUpdatedUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateAllUserFields(1L, user);

        assertEquals(user, updatedUser);
    }

    @Test
    @DisplayName("Deleting a user does not throw an exception")
    public void deleteUserDoesNotThrowException() {
        userService.deleteUser(1L);
    }

    @Test
    @DisplayName("Finding users by valid birth date range does not throw an exception")
    public void findUsersByValidBirthDateRangeDoesNotThrowException() {
        LocalDate from = LocalDate.now().minusDays(1);
        LocalDate to = LocalDate.now();

        userService.findUsersByBirthDateRange(from, to);
    }

    @Test
    @DisplayName("Finding users by invalid birth date range throws IllegalArgumentException")
    public void findUsersByInvalidBirthDateRangeThrowsException() {
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().minusDays(1);

        assertThrows(IllegalArgumentException.class, () -> userService.findUsersByBirthDateRange(from, to));
    }
}