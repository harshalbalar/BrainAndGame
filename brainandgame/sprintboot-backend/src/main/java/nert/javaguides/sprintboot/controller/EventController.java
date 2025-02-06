package nert.javaguides.sprintboot.controller;

import nert.javaguides.sprintboot.model.event.Event;
import nert.javaguides.sprintboot.model.participation.Participation;
import nert.javaguides.sprintboot.model.user.User;
import nert.javaguides.sprintboot.repository.UserRepository;
import nert.javaguides.sprintboot.service.EventService;
import nert.javaguides.sprintboot.service.ParticipationService;
import nert.javaguides.sprintboot.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    private final EventService eventService;
    private final UserRepository userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ParticipationService participationService; // Injected ParticipationService

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    public EventController(
            EventService eventService,
            UserRepository userService,
            JwtUtil jwtUtil,
            UserRepository userRepository,
            ParticipationService participationService // Added ParticipationService
    ) {
        this.eventService = eventService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.participationService = participationService; // Initialize ParticipationService
    }

    // Create a new event
    @PostMapping
    public ResponseEntity<String> createEvent(
            @RequestBody Event event,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtUtil.extractUserId(token);

        // Fetch the User from the DB
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userOpt.get();
        event.setUser(user);
        eventService.saveEvent(event);
        return ResponseEntity.ok("Event successfully saved");
    }

    // Get all events
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    /**
     * Retrieve events created by the authenticated user.
     *
     * @return List of user's events.
     */
    @GetMapping("/my")
    public ResponseEntity<List<Event>> getMyEvents(@RequestHeader("Authorization") String authHeader) {
        // Extract the userId from JWT
        String token = authHeader.replace("Bearer ", "").trim();
        Long userId = jwtUtil.extractUserId(token);
        List<Event> myEvents = eventService.getMyEvents(userId);
        return ResponseEntity.ok(myEvents);
    }

    // Get a specific event by ID
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable Long eventId) {
        logger.info("Get event with id {}", eventId);
        Event event = eventService.getEvent(eventId);
        logger.info("Event found {}", event.toString());
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

    // Get leaderboard for an event
    @GetMapping("/{eventId}/leaderboard")
    public ResponseEntity<List<Participation>> getLeaderboard(@PathVariable Long eventId) {
        List<Participation> leaderboard = participationService.getLeaderboardByEventId(eventId); // Use injected service
        return ResponseEntity.ok(leaderboard);
    }


}