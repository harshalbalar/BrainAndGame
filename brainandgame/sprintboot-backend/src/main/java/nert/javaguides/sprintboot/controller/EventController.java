package nert.javaguides.sprintboot.controller;

import nert.javaguides.sprintboot.model.Event;
import nert.javaguides.sprintboot.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Create a new event
    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody Event event) {
        eventService.saveEvent(event);
        return ResponseEntity.ok("Event successfully saved");
    }

    // Get all events
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    // Get a specific event by ID
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable Long eventId) {
        Event event = eventService.getEvent(eventId);
        return ResponseEntity.ok(event);
    }

    // Toggle the open/close status of an event
    @PutMapping("/{eventId}/toggle-status")
    public ResponseEntity<Event> toggleEventStatus(@PathVariable Long eventId) {
        Event event = eventService.toggleEventStatus(eventId);  // Ensure this is being invoked
        return ResponseEntity.ok(event);  // Return updated event with new status
    }

    // Assign a quiz to an event
    @PostMapping("/{eventId}/assign-quiz/{quizId}")
    public ResponseEntity<String> assignQuizToEvent(
            @PathVariable Long eventId,
            @PathVariable Long quizId) {
        eventService.assignQuizToEvent(eventId, quizId);
        return ResponseEntity.ok("Quiz successfully assigned");
    }
}
