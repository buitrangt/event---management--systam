package org.aibles.eventmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_special")
public class ItemSpecial {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String itemId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long price;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastUpdatedAt;
    private String lastUpdatedBy;
}