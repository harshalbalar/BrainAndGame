package nert.javaguides.sprintboot.service;

import nert.javaguides.sprintboot.model.Event;
import nert.javaguides.sprintboot.model.Participation;
import nert.javaguides.sprintboot.repository.EventRepository;
import nert.javaguides.sprintboot.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public ParticipationService(ParticipationRepository participationRepository, EventRepository eventRepository) {
        this.participationRepository = participationRepository;
        this.eventRepository = eventRepository;
    }

    public Participation createParticipation(Long userId, Long eventId) {
        // Check if the event exists
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Check if the user is already participating in the event
        participationRepository.findByUserIdAndEventId(userId, eventId)
                .ifPresent(existing -> {
                    throw new RuntimeException("User is already participating in this event");
                });

        // Create and save a new participation
        Participation participation = new Participation(userId, eventId, 5);
        return participationRepository.save(participation);
    }
}
