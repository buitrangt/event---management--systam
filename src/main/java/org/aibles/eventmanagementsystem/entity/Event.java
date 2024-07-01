package org.aibles.eventmanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "event")
public class Event {
    @Id
    private String id;

    private String name;
    private String description;
    private String location;
    private Timestamp startTime;
    private Timestamp endTime;
    private String createdBy;
    private String lastUpdatedBy;


}