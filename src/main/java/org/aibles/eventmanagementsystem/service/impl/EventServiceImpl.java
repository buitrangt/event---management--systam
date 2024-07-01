package org.aibles.eventmanagementsystem.service.impl;

import org.aibles.eventmanagementsystem.entity.Event;
import org.aibles.eventmanagementsystem.repository.EventRepository;
import org.aibles.eventmanagementsystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> findById(String id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> findByName(String name) {
        return eventRepository.findByNameContaining(name);
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteById(String id) {
        eventRepository.deleteById(id);
    }
}

