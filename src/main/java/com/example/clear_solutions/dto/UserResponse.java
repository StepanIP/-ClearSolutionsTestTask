package com.example.clear_solutions.dto;

import com.example.clear_solutions.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserResponse {
    private Map<String, String> links;
    private Map<String, Object> data;

    public UserResponse(User user, String selfLink) {
        links = new HashMap<>();
        links.put("self", selfLink);

        data = new HashMap<>();
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
    }
}