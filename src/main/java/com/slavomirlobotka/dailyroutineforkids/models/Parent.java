package com.slavomirlobotka.dailyroutineforkids.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.*;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String surname;

    @NotNull
    @Email(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    @Column(unique = true)
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$")
    @Column(unique=true)
    private String password;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> children;

}