package org.aibles.eventmanagementsystem.service;

import org.aibles.eventmanagementsystem.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> findAll();
    Optional<Event> findById(String id);
    List<Event> findByName(String name);
    Event save(Event event);
    void deleteById(String id);
}