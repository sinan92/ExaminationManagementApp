package com.aon04.backend.models;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "logs")
public class Log {
    @Id
    @SequenceGenerator(name = "logGenerator", sequenceName = "LOG_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logGenerator")
    @Column(name = "id")
    private int id;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "class", nullable = false)
    private String senderClass;

    public String getSenderClass() {
        return senderClass;
    }

    public void setSenderClass(String senderClass) {
        this.senderClass = senderClass;
    }

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
