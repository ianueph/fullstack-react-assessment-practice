package com.daniel.practicebackend.controller;

import com.daniel.practicebackend.entity.Event;
import com.daniel.practicebackend.entity.dto.EventRequest;
import com.daniel.practicebackend.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public Event createEvent(@RequestBody EventRequest request) {
        return eventService.createEvent(request);
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody EventRequest request) {
        return eventService.updateEvent(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
