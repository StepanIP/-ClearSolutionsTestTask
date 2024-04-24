package com.example.clear_solutions.service.impl;

import com.example.clear_solutions.model.User;
import com.example.clear_solutions.repository.UserRepository;
import com.example.clear_solutions.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Value("${user.min.age}")
    private int minAge;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (Period.between(user.getBirthDate(), LocalDate.now()).getYears() < minAge) {
            throw new IllegalArgumentException("User must be at least " + minAge + " years old");
        }
        return userRepository.save(user);
    }

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

    public User updateAllUserFields(Long id, User userUpdates) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setEmail(userUpdates.getEmail());
        user.setFirstName(userUpdates.getFirstName());
        user.setLastName(userUpdates.getLastName());
        user.setBirthDate(userUpdates.getBirthDate());
        user.setAddress(userUpdates.getAddress());
        user.setPhoneNumber(userUpdates.getPhoneNumber());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findUsersByBirthDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("'From' date must be less than 'To' date");
        }
        return userRepository.findByBirthDateBetween(from, to);
    }
}