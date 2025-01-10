package nert.javaguides.sprintboot.service;

import nert.javaguides.sprintboot.model.Event;
import nert.javaguides.sprintboot.model.Quiz;
import nert.javaguides.sprintboot.repository.EventRepository;
import nert.javaguides.sprintboot.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final QuizRepository quizRepository;

    @Autowired
    public EventService(EventRepository eventRepository, QuizRepository quizRepository) {
        this.eventRepository = eventRepository;
        this.quizRepository = quizRepository;
    }

    // Toggle event status (open/closed)
    public Event toggleEventStatus(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Toggle the status
        event.setOpen(!event.isOpen());

        // Save and return the updated event
        return eventRepository.save(event);
    }

    // Save a new event
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get a specific event by ID
    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    // Assign a quiz to an event
    public Event assignQuizToEvent(Long eventId, Long quizId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Check if the quiz is already assigned
        if (event.getQuizzes().contains(quiz)) {
            throw new RuntimeException("Quiz is already assigned to this event");
        }

        event.getQuizzes().add(quiz);
        return eventRepository.save(event);
    }
}
