package com.daniel.practicebackend.controller;

import com.daniel.practicebackend.entity.Event;
import com.daniel.practicebackend.entity.Student;
import com.daniel.practicebackend.entity.dto.StudentRequest;
import com.daniel.practicebackend.entity.dto.StudentRequestIds;
import com.daniel.practicebackend.service.AttendeeService;
import com.daniel.practicebackend.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;
    private final AttendeeService attendeeService;

    public StudentController(StudentService studentService, AttendeeService attendeeService) {
        this.studentService = studentService;
        this.attendeeService = attendeeService;
    }

    @PostMapping
    public Student createStudent(@RequestBody StudentRequest request) {
        return studentService.createStudent(request);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping("/batch")
    public List<Student> getStudentsById(@RequestBody StudentRequestIds request) {
        return studentService.getStudentsById(request);
    }

    @GetMapping("/{id}/events")
    public List<Event> getEventsByStudentId(@PathVariable Long id) {
        return attendeeService.getEventsByStudentId(id);
    }

    @PutMapping("/{id}")
    public Student updateStudentById(@PathVariable Long id, @RequestBody StudentRequest request) {
        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
