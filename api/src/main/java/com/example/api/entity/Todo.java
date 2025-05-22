package com.example.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Table(name = "todos")
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "is_completed")
    private Boolean isCompleted;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}br