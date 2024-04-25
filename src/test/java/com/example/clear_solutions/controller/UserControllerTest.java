package com.example.clear_solutions.controller;

import com.example.clear_solutions.dto.UserResponse;
import com.example.clear_solutions.model.User;
import com.example.clear_solutions.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    @DisplayName("Creating a user returns a created user response")
    public void createUserReturnsCreatedUser() {
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<UserResponse> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Updating user fields returns an updated user response")
    public void updateUserFieldsReturnsUpdatedUser() {
        User user = new User();
        Long id = 1L;
        when(userService.updateUserFields(id, user)).thenReturn(user);

        ResponseEntity<UserResponse> response = userController.updateUserFields(id, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Updating all user fields returns an updated user response")
    public void updateAllUserFieldsReturnsUpdatedUser() {
        User user = new User();
        Long id = 1L;
        when(userService.updateAllUserFields(id, user)).thenReturn(user);

        ResponseEntity<UserResponse> response = userController.updateAllUserFields(id, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Deleting a user returns a no content response")
    public void deleteUserReturnsNoContent() {
        Long id = 1L;

        ResponseEntity<Void> response = userController.deleteUser(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Finding users by birth date range returns a list of user responses")
    public void findUsersByBirthDateRangeReturnsUsers() {
        LocalDate from = LocalDate.now().minusDays(1);
        LocalDate to = LocalDate.now();
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);
        when(userService.findUsersByBirthDateRange(from, to)).thenReturn(users);

        ResponseEntity<List<UserResponse>> response = userController.findUsersByBirthDateRange(from, to);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
}