package com.daniel.practicebackend.controller;

import com.daniel.practicebackend.entity.Attendee;
import com.daniel.practicebackend.entity.dto.AttendeeRequest;
import com.daniel.practicebackend.service.AttendeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("attendees")
public class AttendeeController {

    private final AttendeeService attendeeService;

    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @PostMapping
    public Attendee createAttendee(@RequestBody AttendeeRequest request) {
        return attendeeService.createAttendee(request);
    }

    @GetMapping("/{id}")
    public Attendee getAttendeeById(@PathVariable Long id) {
        return attendeeService.getAttendee(id);
    }

    @GetMapping
    public List<Attendee> getAttendees() {
        return attendeeService.getAttendees();
    }

    @DeleteMapping("/{id}")
    public void deleteAttendee(@PathVariable Long id) {
        attendeeService.deleteAttendee(id);
    }
}
