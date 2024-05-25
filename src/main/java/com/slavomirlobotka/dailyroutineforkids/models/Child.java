package com.slavomirlobotka.dailyroutineforkids.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString(exclude = "user")
public class Child {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is mandatory")
  @Column(unique = true)
  private String name;

  private Integer age;

  private String gender;

  @JsonIgnore @ManyToOne private User user;

  @JsonIgnore
  @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
  private List<Schedule> schedules;
}
