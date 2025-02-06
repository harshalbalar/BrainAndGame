package nert.javaguides.sprintboot.service;

import nert.javaguides.sprintboot.model.participation.Participation;
import nert.javaguides.sprintboot.model.user.User;
import nert.javaguides.sprintboot.model.event.Event;

import nert.javaguides.sprintboot.repository.EventRepository;
import nert.javaguides.sprintboot.repository.ParticipationRepository;
import nert.javaguides.sprintboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    public Participation createParticipation(Long userId, Long eventId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id " + userId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new Exception("Event not found with id " + eventId));

        // Check if participation already exists
        Optional<Participation> existingParticipation = participationRepository.findByUserIdAndEventId(userId, eventId);
        if (existingParticipation.isPresent()) {
            throw new IllegalStateException("User is already participating in this event.");
        }

        Participation participation = new Participation(user, event);
        return participationRepository.save(participation);
    }

    public void deleteParticipation(Long participationId) throws Exception {
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new Exception("Participation not found with id " + participationId));
        participationRepository.delete(participation);
    }

    public List<Participation> getParticipationsByUserId(Long userId) {
        // Implement a method in repository if needed
        // For simplicity, fetching all and filtering
        return participationRepository.findAll()
                .stream()
                .filter(p -> p.getUser().getId().equals(userId))
                .toList();
    }

    public Optional<Participation> getParticipationById(Long participationId) {
        // Implement a method in repository if needed
        // For simplicity, fetching all and filtering
        return participationRepository.findById(participationId);
    }

    public List<Participation> getParticipationsByEventId(Long eventId) {
        // Implement a method in repository if needed
        return participationRepository.findAll()
                .stream()
                .filter(p -> p.getEvent().getId().equals(eventId))
                .toList();
    }
    public List<Participation> getLeaderboardByEventId(Long eventId) {
        return participationRepository.findByEventIdOrderByPointsDesc(eventId);
    }
}
