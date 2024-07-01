package org.aibles.eventmanagementsystem.controller;

import org.aibles.eventmanagementsystem.entity.EventParticipant;
import org.aibles.eventmanagementsystem.service.EventParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event-participants")
public class EventParticipantController {
    @Autowired
    private EventParticipantService eventParticipantService;

    @GetMapping("/{eventId}")
    public List<EventParticipant> getParticipants(@PathVariable String eventId) {
        return eventParticipantService.findByEventId(eventId);
    }

    @PostMapping
    public EventParticipant joinEvent(@RequestBody EventParticipant eventParticipant) {
        return eventParticipantService.joinEvent(eventParticipant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> leaveEvent(@PathVariable String id) {
        eventParticipantService.leaveEvent(id);
        return ResponseEntity.noContent().build();
    }
}
