package com.slavomirlobotka.dailyroutineforkids.email;

import com.slavomirlobotka.dailyroutineforkids.models.Parent;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmailConfirmationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String token;
  private LocalDateTime createdAt;
  private LocalDateTime expiresAt;
  private LocalDateTime validatedAt;

  @ManyToOne
  @JoinColumn(name = "parent_id", nullable = false)
  private Parent parent;
}
