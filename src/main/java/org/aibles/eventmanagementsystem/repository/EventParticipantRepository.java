package org.aibles.eventmanagementsystem.repository;

import org.aibles.eventmanagementsystem.entity.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, String> {
    List<EventParticipant> findByEventId(String eventId);
}