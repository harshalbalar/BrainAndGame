package nert.javaguides.sprintboot.controller;

import nert.javaguides.sprintboot.model.Participation;
import nert.javaguides.sprintboot.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participations")
public class ParticipationController {

    private final ParticipationService participationService;

    @Autowired
    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping
    public ResponseEntity<Participation> createParticipation(
            @RequestParam Long userId,
            @RequestParam Long eventId) {
        Participation participation = participationService.createParticipation(userId, eventId);
        return ResponseEntity.ok(participation);
    }
}
