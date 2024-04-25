package com.example.clear_solutions.controller;

import com.example.clear_solutions.dto.UserResponse;
import com.example.clear_solutions.model.User;
import com.example.clear_solutions.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        String requestUrl = getRequestUrl();
        UserResponse response = new UserResponse(createdUser, requestUrl);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<UserResponse> updateUserFields(@PathVariable Long id, @RequestBody User userUpdates) {
        User updatedUser = userService.updateUserFields(id, userUpdates);
        String requestUrl = getRequestUrl();
        UserResponse response = new UserResponse(updatedUser, requestUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("update-all/{id}/")
    public ResponseEntity<UserResponse> updateAllUserFields(@PathVariable Long id, @RequestBody User userUpdates) {
        User updatedUser = userService.updateAllUserFields(id, userUpdates);
        String requestUrl = getRequestUrl();
        UserResponse response = new UserResponse(updatedUser, requestUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> findUsersByBirthDateRange(@RequestBody LocalDate from, @RequestBody LocalDate to) {
        List<User> users = userService.findUsersByBirthDateRange(from, to);
        String requestUrl = getRequestUrl();
        List<UserResponse> responses = users.stream()
                .map(user -> new UserResponse(user, requestUrl))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    private String getRequestUrl() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest().getRequestURL().toString();
        }
        return null;
    }
}