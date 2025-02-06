package nert.javaguides.sprintboot.controller;

import nert.javaguides.sprintboot.model.participation.Participation;

import nert.javaguides.sprintboot.repository.ParticipationRepository;
import nert.javaguides.sprintboot.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participations")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private ParticipationRepository participationRepository;

    // Get all participations
    @GetMapping
    public List<Participation> getAllParticipations() {
        return participationService.getAllParticipations();
    }

    // Create a new participation
    @PostMapping
    public ResponseEntity<Participation> createParticipation(@RequestParam Long userId, @RequestParam Long eventId) {
        try {
            Participation participation = participationService.createParticipation(userId, eventId);
            return ResponseEntity.ok(participation);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{participationId}")
    public ResponseEntity<Participation> updateParticipation(
            @PathVariable Long participationId,
            @RequestParam Integer amountOfTickets
    ) {
        Optional<Participation> participationOption = participationService.getParticipationById(participationId);
        if (participationOption.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Participation participation = participationOption.get();
        Integer originalAmountOfTickets = participation.getTickets();
        Integer newAmountOfTickets = amountOfTickets + originalAmountOfTickets;
        participation.setTickets(newAmountOfTickets);
        participation.setQuizPlayed(true);
        participationRepository.save(participation);

        return ResponseEntity.ok(participation);


    }

    // Delete a participation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipation(@PathVariable Long id) {
        try {
            participationService.deleteParticipation(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get participations by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Participation>> getParticipationsByUserId(@PathVariable Long userId) {
        List<Participation> participations = participationService.getParticipationsByUserId(userId);
        return ResponseEntity.ok(participations);
    }

    // Get participations by user ID
    @GetMapping("/{participationId}")
    public ResponseEntity<Participation> getParticipationById(@PathVariable Long participationId) {
        Optional<Participation> participation = participationService.getParticipationById(participationId);
        if (participation.isPresent()) {
            Participation p = participation.get();
            return ResponseEntity.ok(p);
        }
       return ResponseEntity.notFound().build();
    }

    // Get participations by event ID
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Participation>> getParticipationsByEventId(@PathVariable Long eventId) {
        List<Participation> participations = participationService.getParticipationsByEventId(eventId);
        return ResponseEntity.ok(participations);
    }
}
