package ru.netology.diplom.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Null
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Email
    @Column(unique = true, nullable = false)
    private String login;
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String password;
}
