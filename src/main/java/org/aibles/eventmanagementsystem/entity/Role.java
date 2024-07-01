package org.aibles.eventmanagementsystem.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String createdBy;
    private Long createdAt;
    private String lastUpdatedBy;
    private Long lastUpdatedAt;

    public Role(){
    }

    public Role(String id, String name, String createdBy, Long createdAt, String lastUpdatedBy, Long lastUpdatedAt) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
