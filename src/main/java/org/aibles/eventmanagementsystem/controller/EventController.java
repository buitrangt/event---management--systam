package org.aibles.eventmanagementsystem.controller;

import org.aibles.eventmanagementsystem.entity.Event;
import org.aibles.eventmanagementsystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        return eventService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Event> searchEvents(@RequestParam String name) {
        return eventService.findByName(name);
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.save(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable String id, @RequestBody Event eventDetails) {
        return eventService.findById(id)
                .map(event -> {
                    event.setName(eventDetails.getName());
                    event.setDescription(eventDetails.getDescription());
                    event.setLocation(eventDetails.getLocation());
                    event.setStartTime(eventDetails.getStartTime());
                    event.setEndTime(eventDetails.getEndTime());
                    event.setCreatedBy(eventDetails.getCreatedBy());
                    event.setLastUpdatedBy(eventDetails.getLastUpdatedBy());
                    return ResponseEntity.ok(eventService.save(event));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable String id) {
        return eventService.findById(id)
                .map(event -> {
                    eventService.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
