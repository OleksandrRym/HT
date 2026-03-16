package com.rymar.ht.domain.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "activity")
public class Activity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  private Double count_ex;

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;
}
