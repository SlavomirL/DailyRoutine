package com.slavomirlobotka.dailyroutineforkids.models.roles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("user")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleUser extends RoleVisitor {}
