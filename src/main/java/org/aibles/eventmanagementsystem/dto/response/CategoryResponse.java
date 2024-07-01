package org.aibles.eventmanagementsystem.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponse {
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastUpdatedAt;
    private String lastUpdatedBy;
}