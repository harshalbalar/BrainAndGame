package nert.javaguides.sprintboot.service;

import nert.javaguides.sprintboot.model.event.Event;
import nert.javaguides.sprintboot.model.quiz.Quiz;
import nert.javaguides.sprintboot.model.user.User;
import nert.javaguides.sprintboot.repository.EventRepository;
import nert.javaguides.sprintboot.repository.QuizRepository;
import nert.javaguides.sprintboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    public EventService(EventRepository eventRepository, QuizRepository quizRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    // Toggle event status (open/closed)
    public Event toggleEventStatus(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Toggle the status
        event.setOpen(!event.getOpen());

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
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        logger.info(event.toString());

        return event;
    }

    /**
     * Retrieves events created by the currently authenticated user.
     *
     * @return List of events created by the user.
     */
    public List<Event> getMyEvents(Long id) {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return eventRepository.findAllByUserId(user.getId());
        } else {
            throw new RuntimeException("User not found"); // Consider a custom exception
        }
    }

    // Assign a quiz to an event
    public Event assignQuizToEvent(Long eventId, Long quizId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Check if the quiz is already assigned
        if (Objects.equals(event.getQuiz(), quiz)) {
            throw new RuntimeException("Quiz is already assigned to this event");
        }

        event.setQuiz(quiz);
        return eventRepository.save(event);
    }
}
