package com.daniel.practicebackend.controller;

import com.daniel.practicebackend.entity.Event;
import com.daniel.practicebackend.entity.Student;
import com.daniel.practicebackend.entity.dto.EventRequest;
import com.daniel.practicebackend.entity.dto.EventRequestIds;
import com.daniel.practicebackend.service.AttendeeService;
import com.daniel.practicebackend.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("events")
public class EventController {

    private final EventService eventService;
    private final AttendeeService attendeeService;

    public EventController(EventService eventService, AttendeeService attendeeService) {
        this.eventService = eventService;
        this.attendeeService = attendeeService;
    }

    @PostMapping
    public Event createEvent(@RequestBody EventRequest request) {
        return eventService.createEvent(request);
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    @PostMapping("/batch")
    public List<Event> getEventsById(@RequestBody EventRequestIds request) {
        return eventService.getEventsByIds(request);
    }

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsByEventId(@PathVariable Long id) {
        return attendeeService.getStudentsByEventId(id);
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
