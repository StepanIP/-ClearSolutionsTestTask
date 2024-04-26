package com.example.clear_solutions.controller;

import com.example.clear_solutions.dto.UserResponse;
import com.example.clear_solutions.model.User;
import com.example.clear_solutions.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserController is a REST controller that handles HTTP requests related to User entities.
 * It uses UserService to perform operations on User entities.
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructs a UserController with the specified UserService.
     *
     * @param userService the UserService to be used by the UserController
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles POST requests to create a new User.
     *
     * @param user the User to be created
     * @return a ResponseEntity containing a UserResponse with the created User and the request URL
     */
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        String requestUrl = getRequestUrl();
        UserResponse response = new UserResponse(createdUser, requestUrl);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Handles PATCH requests to update fields of an existing User.
     *
     * @param id          the ID of the User to be updated
     * @param userUpdates the User object containing the updated fields
     * @return a ResponseEntity containing a UserResponse with the updated User and the request URL
     */
    @PatchMapping("update/{id}")
    public ResponseEntity<UserResponse> updateUserFields(@PathVariable Long id, @RequestBody User userUpdates) {
        User updatedUser = userService.updateUserFields(id, userUpdates);
        String requestUrl = getRequestUrl();
        UserResponse response = new UserResponse(updatedUser, requestUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles PUT requests to update all fields of an existing User.
     *
     * @param id          the ID of the User to be updated
     * @param userUpdates the User object containing the updated fields
     * @return a ResponseEntity containing a UserResponse with the updated User and the request URL
     */
    @PutMapping("update-all/{id}")
    public ResponseEntity<UserResponse> updateAllUserFields(@PathVariable Long id, @RequestBody User userUpdates) {
        User updatedUser = userService.updateAllUserFields(id, userUpdates);
        String requestUrl = getRequestUrl();
        UserResponse response = new UserResponse(updatedUser, requestUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles DELETE requests to delete a User.
     *
     * @param id the ID of the User to be deleted
     * @return a ResponseEntity with a NO_CONTENT status
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handles GET requests to find Users by a range of birth dates.
     *
     * @param from the start of the birth date range
     * @param to   the end of the birth date range
     * @return a ResponseEntity containing a list of UserResponses with the found Users and the request URL
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> findUsersByBirthDateRange(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        List<User> users = userService.findUsersByBirthDateRange(from, to);
        String requestUrl = getRequestUrl();
        List<UserResponse> responses = users.stream()
                .map(user -> new UserResponse(user, requestUrl))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    /**
     * Gets the URL of the current request.
     *
     * @return the URL of the current request, or null if the request attributes are not available
     */
    private String getRequestUrl() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest().getRequestURL().toString();
        }
        return null;
    }
}