package com.slavomirlobotka.dailyroutineforkids.models.roles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("visitor")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleVisitor extends Role {}
