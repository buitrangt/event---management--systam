package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.entity.EventParticipant;

import java.util.List;

public interface EventParticipantService {
    List<EventParticipant> findByEventId(String eventId);
    EventParticipant joinEvent(EventParticipant eventParticipant);
    void leaveEvent(String id);
}
