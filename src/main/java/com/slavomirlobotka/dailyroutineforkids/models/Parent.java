package com.slavomirlobotka.dailyroutineforkids.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

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

  //    @CreatedDate
  //    @Column(nullable = false, updatable = false)
  //    private LocalDateTime createdAt;
  //
  //    @LastModifiedDate
  //    @Column(insertable = false)
  //    private LocalDateTime modifiedAt;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  private List<Child> children;
}
