package com.example.clear_solutions.service;

import com.example.clear_solutions.model.User;
import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUserFields(Long id, User userUpdates);
    User updateAllUserFields(Long id, User userUpdates);
    void deleteUser(Long id);
    List<User> findUsersByBirthDateRange(LocalDate from, LocalDate to);
}
