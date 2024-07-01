package org.aibles.eventmanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "event_participant")
public class EventParticipant {
    @Id
    private String id;

    private String eventId;
    private String userId;


}
