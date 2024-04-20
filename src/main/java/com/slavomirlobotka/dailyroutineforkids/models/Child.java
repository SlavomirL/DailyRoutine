package com.slavomirlobotka.dailyroutineforkids.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private Integer age;

    private String gender;

    @ManyToOne
    private Parent parent;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

}