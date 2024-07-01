package org.aibles.eventmanagementsystem.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MenuResponse {
    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastUpdatedAt;
    private String lastUpdatedBy;
}
