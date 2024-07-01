package org.aibles.eventmanagementsystem.service.impl;

import org.aibles.eventmanagementsystem.entity.EventParticipant;
import org.aibles.eventmanagementsystem.repository.EventParticipantRepository;
import org.aibles.eventmanagementsystem.service.EventParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventParticipantServiceImpl implements EventParticipantService {
    @Autowired
    private EventParticipantRepository eventParticipantRepository;

    @Override
    public List<EventParticipant> findByEventId(String eventId) {
        return eventParticipantRepository.findByEventId(eventId);
    }

    @Override
    public EventParticipant joinEvent(EventParticipant eventParticipant) {
        return eventParticipantRepository.save(eventParticipant);
    }

    @Override
    public void leaveEvent(String id) {
        eventParticipantRepository.deleteById(id);
    }
}

