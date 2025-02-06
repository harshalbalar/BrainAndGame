package nert.javaguides.sprintboot.controller;

import nert.javaguides.sprintboot.hubs.GameNotificationHub;
import nert.javaguides.sprintboot.model.game.Game;
import nert.javaguides.sprintboot.model.participation.Participation;
import nert.javaguides.sprintboot.repository.GameRepository;
import nert.javaguides.sprintboot.service.GameService;
import nert.javaguides.sprintboot.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;
    private final ParticipationService participationService;
    private final GameRepository gameRepository;
    private final GameNotificationHub gameNotificationHub;

    @Autowired
    public GameController(GameService gameService,
                          ParticipationService participationService,
                          GameRepository gameRepository, GameNotificationHub gameNotificationHub) {
        this.gameService = gameService;
        this.participationService = participationService;
        this.gameRepository = gameRepository;
        this.gameNotificationHub = gameNotificationHub;
    }

    @PostMapping("/initiate")
    public ResponseEntity<Game> initiateGame(
            @RequestParam Long initiatorId,
            @RequestParam Long joinerId,
            @RequestParam(defaultValue = "3") Integer numberOfQuestions
    ) {
        Optional<Participation> initiatorOpt = participationService.getParticipationById(initiatorId);
        Optional<Participation> joinerOpt = participationService.getParticipationById(joinerId);

        if (initiatorOpt.isEmpty() || joinerOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Game game = gameService.initiateGame(initiatorOpt.get(), joinerOpt.get(), numberOfQuestions);
        return ResponseEntity.ok(game);
    }

    @PutMapping("/{gameId}/accept")
    public ResponseEntity<Game> acceptGame(@PathVariable Long gameId) {
        Optional<Game> gameOpt = gameService.getGameById(gameId);
        if (gameOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Game game = gameOpt.get();
        // Only allow accept if it's still PENDING
        if (!"PENDING".equals(game.getStatus())) {
            // Return 400 if we try to accept a non-pending game
            return ResponseEntity.badRequest().body(game);
        }

        // Mark as ONGOING
        game.setStatus("ONGOING");
        game.setStartedAt(java.time.LocalDateTime.now());
        game = gameRepository.save(game);

        // Optionally broadcast to both players that the game is now accepted
        gameNotificationHub.broadcastGameAccepted(game);

        return ResponseEntity.ok(game);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable Long gameId) {
        return gameService.getGameById(gameId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ongoing and completed
    @GetMapping("/participant/{participantId}/ongoing")
    public List<Game> getOngoingGames(@PathVariable Long participantId) {
        return gameRepository.findOngoingGamesForParticipant(participantId);
    }

    @GetMapping("/participant/{participantId}/history")
    public List<Game> getCompletedGamesForParticipant(@PathVariable Long participantId) {
        return gameRepository.findCompletedGamesForParticipant(participantId);
    }

    @PutMapping("/{gameId}/update")
    public ResponseEntity<Game> updateGameScore(
            @PathVariable Long gameId,
            @RequestParam Long participantId,
            @RequestParam Boolean isCorrect
    ) {
        Optional<Game> gameOpt = gameService.getGameById(gameId);
        if (gameOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Game game = gameOpt.get();
        gameService.updateGameScore(game, participantId, isCorrect);

        if (gameService.isGameCompleted(game)) {
            gameService.determineWinner(game);
        }
        return ResponseEntity.ok(game);
    }
}
