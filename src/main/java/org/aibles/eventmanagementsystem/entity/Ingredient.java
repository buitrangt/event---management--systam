package org.aibles.eventmanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ingredient")
@Data
public class Ingredient {

    @Id
    private String id;

    private String name;

    private Long createdAt;

    private String createdBy;

    private Long lastUpdatedAt;

    private String lastUpdatedBy;
}
