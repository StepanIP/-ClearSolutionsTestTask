package com.example.clear_solutions.repository;

import com.example.clear_solutions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByBirthDateBetween(LocalDate from, LocalDate to);
}
