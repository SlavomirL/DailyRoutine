package com.slavomirlobotka.dailyroutineforkids.models;

import com.slavomirlobotka.dailyroutineforkids.models.roles.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Parent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is mandatory")
  private String firstName;

  @NotBlank(message = "Surname is mandatory")
  private String surname;

  @NotBlank(message = "Email is mandatory")
  @Email(
      regexp =
          "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
  @Column(unique = true)
  private String email;

  @NotBlank(message = "Password is mandatory")
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$")
  @Column(unique = true)
  private String password;

  private boolean enabled;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  private List<Child> children;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;
}
