package com.daniel.practicebackend.service;

import com.daniel.practicebackend.entity.Student;
import com.daniel.practicebackend.entity.dto.StudentRequest;
import com.daniel.practicebackend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(StudentRequest request) {
        Student student = new Student();

        student.setName(request.name());
        student.setStudentNumber(request.studentNumber());

        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Student with id (%d) not found", id)
                ));
    }

    public Student updateStudent(Long id, StudentRequest request) {
        Student student = getStudent(id);

        student.setName(request.name());
        student.setStudentNumber(request.studentNumber());

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = getStudent(id);
        studentRepository.delete(student);
    }
}
