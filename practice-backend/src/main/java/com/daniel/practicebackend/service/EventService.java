package com.daniel.practicebackend.service;

import com.daniel.practicebackend.entity.Event;
import com.daniel.practicebackend.entity.dto.EventRequest;
import com.daniel.practicebackend.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(EventRequest request) {
        Event event = new Event();

        event.setName(request.name());
        event.setDescription(request.description());

        return eventRepository.save(event);
    }

    public Event getEvent(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Event of id (%s) not found.", id)
                ));
    }

    public Event updateEvent(Long id, EventRequest request) {
        Event event = getEvent(id);
        
        event.setName(request.name());
        event.setDescription(request.description());
        
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = getEvent(id);
        eventRepository.delete(event);
    }
}
