package com.example.clear_solutions.repository;

import com.example.clear_solutions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * UserRepository is a Spring Data JPA repository for User entities.
 * It extends JpaRepository, which provides JPA related methods such as save(), findOne(), findAll(), count(), delete() etc.
 * It also declares a custom method to find Users by a range of birth dates.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds Users whose birthdate is between the specified from and to dates.
     *
     * @param from the start of the birthdate range
     * @param to the end of the birthdate range
     * @return a list of Users whose birthdate is between the specified from and to dates
     */
    List<User> findByBirthDateBetween(LocalDate from, LocalDate to);
}