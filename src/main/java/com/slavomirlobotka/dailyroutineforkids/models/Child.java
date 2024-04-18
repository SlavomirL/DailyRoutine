package com.slavomirlobotka.dailyroutineforkids.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private Integer age;

    private String gender;

    @ManyToOne
    private Parent parent;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

}