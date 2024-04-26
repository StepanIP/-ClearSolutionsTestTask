package com.example.clear_solutions.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

/**
 * User is an entity class that represents a user in the application.
 * It contains fields for the user's ID, email, first name, last name, birthdate, address, and phone number.
 * The ID field is annotated with @Id and @GeneratedValue to indicate that it is the primary key and is automatically generated.
 * The email, first name, last name, and birthdate fields are annotated with validation constraints.
 * The first name, last name, birthdate, and phone number fields are mapped to specific column names in the database.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {

    /**
     * The ID of the user.
     * It is the primary key and is automatically generated.
     */
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The email of the user.
     * It must be a well-formed email address and is required.
     */
    @Email
    @NotBlank
    private String email;

    /**
     * The first name of the user.
     * It is required and is mapped to the "first_name" column in the database.
     */
    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    /**
     * The last name of the user.
     * It is required and is mapped to the "last_name" column in the database.
     */
    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    /**
     * The birthdate of the user.
     * It must be a past date and is required.
     * It is mapped to the "birth_date" column in the database.
     */
    @Past
    @NotNull
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * The address of the user.
     */
    private String address;

    /**
     * The phone number of the user.
     * It is mapped to the "phone_number" column in the database.
     */
    @Column(name = "phone_number")
    private String phoneNumber;
}