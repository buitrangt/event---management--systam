package org.aibles.eventmanagementsystem.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemSpecialResponse {
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
