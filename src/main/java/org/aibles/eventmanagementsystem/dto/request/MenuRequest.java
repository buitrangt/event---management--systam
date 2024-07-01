package org.aibles.eventmanagementsystem.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MenuRequest {
    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastUpdatedAt;
    private String lastUpdatedBy;
}
