package org.aibles.eventmanagementsystem.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemResponse {
    private String id;
    private String name;
    private String description;
    private long price;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastUpdatedAt;
    private String lastUpdatedBy;
}