package com.example.clear_solutions.controller;

import com.example.clear_solutions.dto.UserResponse;
import com.example.clear_solutions.model.User;
import com.example.clear_solutions.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest is a test class for UserController.
 * It uses Spring's MockMvc to simulate HTTP requests and responses.
 * It also uses Mockito to mock the UserService.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    /**
     * The MockMvc instance used to simulate HTTP requests and responses.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * The mocked UserService.
     */
    @MockBean
    private UserService userService;

    /**
     * The ObjectMapper used to convert objects to and from JSON.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Sets up the tests.
     * This method is run before each test.
     * It sets up the behavior of the mocked UserService.
     */
    @BeforeEach
    public void setup() {
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);
        when(userService.updateUserFields(1L, user)).thenReturn(user);
        when(userService.updateAllUserFields(1L, user)).thenReturn(user);
        when(userService.findUsersByBirthDateRange(LocalDate.now().minusDays(1), LocalDate.now())).thenReturn(Arrays.asList(user, user));
    }

    /**
     * Tests that creating a user returns a created user response.
     */
    @Test
    @DisplayName("Creating a user returns a created user response")
    public void createUserReturnsCreatedUser() throws Exception {
        User user = new User();

        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    /**
     * Tests that updating user fields returns an updated user response.
     */
    @Test
    @DisplayName("Updating user fields returns an updated user response")
    public void updateUserFieldsReturnsUpdatedUser() throws Exception {
        User user = new User();

        mockMvc.perform(patch("/api/v1/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    /**
     * Tests that updating all user fields returns an updated user response.
     */
    @Test
    @DisplayName("Updating all user fields returns an updated user response")
    public void updateAllUserFieldsReturnsUpdatedUser() throws Exception {
        User user = new User();

        mockMvc.perform(put("/api/v1/users/update-all/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    /**
     * Tests that deleting a user returns a no content response.
     */
    @Test
    @DisplayName("Deleting a user returns a no content response")
    public void deleteUserReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/users/delete/1"))
                .andExpect(status().isNoContent());
    }

    /**
     * Tests that finding users by birthdate range returns a list of user responses.
     */
    @Test
    @DisplayName("Finding users by birth date range returns a list of user responses")
    public void findUsersByBirthDateRangeReturnsUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .param("from", LocalDate.now().minusDays(1).toString())
                        .param("to", LocalDate.now().toString()))
                .andExpect(status().isOk());
    }
}