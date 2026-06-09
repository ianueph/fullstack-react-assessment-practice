package com.daniel.practicebackend.service;

import com.daniel.practicebackend.entity.Attendee;
import com.daniel.practicebackend.entity.Event;
import com.daniel.practicebackend.entity.Student;
import com.daniel.practicebackend.entity.dto.AttendeeRequest;
import com.daniel.practicebackend.repository.AttendeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final EventService eventService;
    private final StudentService studentService;

    public AttendeeService(AttendeeRepository attendeeRepository, EventService eventService, StudentService studentService) {
        this.attendeeRepository = attendeeRepository;
        this.eventService = eventService;
        this.studentService = studentService;
    }

    public Attendee createAttendee(AttendeeRequest request) {
        Attendee attendee = new Attendee();
        Event event = eventService.getEvent(request.eventId());
        Student student = studentService.getStudent(request.studentId());

        attendee.setEvent(event);
        attendee.setStudent(student);

        return attendeeRepository.save(attendee);
    }

    public Attendee getAttendee(Long id) {
        return attendeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Attendee of id (%d) was not found", id)
                ));
    }

    public List<Attendee> getAttendees() {
        return attendeeRepository.findAll();
    }

    public List<Student> getStudentsByEventId(Long eventId) {
        return attendeeRepository
                .getAttendeesByEvent_Id(eventId)
                .stream()
                .map(Attendee::getStudent)
                .toList();
    }

    public List<Event> getEventsByStudentId(Long studentId) {
        return attendeeRepository
                .getAttendeesByStudent_Id(studentId)
                .stream()
                .map(Attendee::getEvent)
                .toList();
    }

    public void deleteAttendee(Long id) {
        attendeeRepository.delete(getAttendee(id));
    }
}
