package com.daniel.practicebackend.repository;

import com.daniel.practicebackend.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    List<Attendee> getAttendeesByEvent_Id(Long eventId);

    List<Attendee> getAttendeesByStudent_Id(Long studentId);
}
