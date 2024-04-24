package com.example.clear_solutions.controller;

import com.example.clear_solutions.model.User;
import com.example.clear_solutions.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final ServletRequestAttributes attributes;

    public UserController(UserService userService) {
        this.userService = userService;
        attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        Map<String, Object> response = createJsonApiResponse(createdUser, attributes.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Map<String, Object>> updateUserFields(@PathVariable Long id, @RequestBody User userUpdates) {
        User updatedUser = userService.updateUserFields(id, userUpdates);
        Map<String, Object> response = createJsonApiResponse(updatedUser, attributes.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("update-all/{id}/")
    public ResponseEntity<Map<String, Object>> updateAllUserFields(@PathVariable Long id, @RequestBody User userUpdates) {
        User updatedUser = userService.updateAllUserFields(id, userUpdates);
        Map<String, Object> response = createJsonApiResponse(updatedUser, attributes.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        Map<String, Object> response = createJsonApiResponse(null, attributes.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> findUsersByBirthDateRange(@RequestBody LocalDate from, @RequestBody LocalDate to) {
        List<User> users = userService.findUsersByBirthDateRange(from, to);
        List<Map<String, Object>> responses = users.stream()
                .map(user -> createJsonApiResponse(user, attributes.getRequest().getRequestURL().toString()))
                .toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    private Map<String, Object> createJsonApiResponse(User user, String selfLink) {
        Map<String, Object> response = new HashMap<>();

        Map<String, String> links = new HashMap<>();
        links.put("self", selfLink);

        response.put("links", links);

        Map<String, Object> data = new HashMap<>();
        data.put("type", "user");
        if (user != null) {
            data.put("id", user.getId());

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("email", user.getEmail());
            attributes.put("firstName", user.getFirstName());
            attributes.put("lastName", user.getLastName());
            attributes.put("birthDate", user.getBirthDate());
            if (user.getAddress() != null) {
                attributes.put("address", user.getAddress());
            }
            if (user.getPhoneNumber() != null) {
                attributes.put("phoneNumber", user.getPhoneNumber());
            }

            data.put("attributes", attributes);
        }
        response.put("data", data);
        return response;
    }
}