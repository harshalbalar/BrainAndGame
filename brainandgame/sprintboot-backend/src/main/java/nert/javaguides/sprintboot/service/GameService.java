package nert.javaguides.sprintboot.service;

import nert.javaguides.sprintboot.hubs.GameNotificationHub;
import nert.javaguides.sprintboot.model.game.Game;
import nert.javaguides.sprintboot.model.game.GameQuestion;
import nert.javaguides.sprintboot.model.participation.Participation;
import nert.javaguides.sprintboot.repository.GameQuestionRepository;
import nert.javaguides.sprintboot.repository.GameRepository;
import nert.javaguides.sprintboot.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameQuestionRepository gameQuestionRepository;
    private final GameNotificationHub gameNotificationHub;
    private final ParticipationRepository participationRepository;

    @Autowired
    public GameService(GameRepository gameRepository,
                       GameQuestionRepository gameQuestionRepository,
                       GameNotificationHub gameNotificationHub, ParticipationRepository participationRepository) {
        this.gameRepository = gameRepository;
        this.gameQuestionRepository = gameQuestionRepository;
        this.gameNotificationHub = gameNotificationHub;
        this.participationRepository = participationRepository;
    }

    @Transactional
    public Game initiateGame(Participation initiator, Participation joiner, int numberOfQuestions) {
        Game game = new Game(initiator, joiner);

        List<GameQuestion> randomQuestions = gameQuestionRepository.findRandomQuestions(numberOfQuestions);
        // validate randomQuestions ...

        game.setGameQuestions(randomQuestions);

        // Mark it as PENDING at creation
        game.setStatus("PENDING");
        // startedAt stays null until the opponent accepts

        Game savedGame = gameRepository.save(game);

        // Broadcast "NEW_GAME" if you like, so the joiner sees there's a pending game
        gameNotificationHub.broadcastNewGame(savedGame);
        return savedGame;
    }

    public Optional<Game> getGameById(Long gameId) {
        return gameRepository.findById(gameId);
    }

    @Transactional
    public void updateGameScore(Game game, Long participantId, Boolean isCorrect) {
        // Update score if correct
        if (game.getInitiator().getId().equals(participantId)) {
            game.setInitiatorAnswers(game.getInitiatorAnswers() + 1);
            if (isCorrect) {
                game.setInitiatorScore(game.getInitiatorScore() + 1);
            }
        }
        else if (game.getJoiner().getId().equals(participantId)) {
            game.setJoinerAnswers(game.getJoinerAnswers() + 1);
            if (isCorrect) {
                game.setJoinerScore(game.getJoinerScore() + 1);
            }
        }
        gameRepository.save(game);
    }

    public boolean isGameCompleted(Game game) {
        int totalQuestions = game.getGameQuestions().size();
        // The game ends once BOTH have answered *all* questions
        boolean initiatorDone = (game.getInitiatorAnswers() >= totalQuestions);
        boolean joinerDone = (game.getJoinerAnswers() >= totalQuestions);
        return initiatorDone && joinerDone;
    }

    @Transactional
    public void determineWinner(Game game) {
        // 1) Mark the game as COMPLETED
        game.setStatus("COMPLETED");
        game.setEndedAt(java.time.LocalDateTime.now());
        gameRepository.save(game);

        // 2) Subtract a ticket from both participants
        Participation initiator = game.getInitiator();
        Participation joiner = game.getJoiner();

        initiator.setTickets(initiator.getTickets() - 1);
        joiner.setTickets(joiner.getTickets() - 1);

        // 3) Determine who gets points
        Long winnerId = null;
        int initiatorScore = game.getInitiatorScore();
        int joinerScore = game.getJoinerScore();

        if (initiatorScore > joinerScore) {
            // Initiator wins => +3 points
            winnerId = initiator.getId();
            initiator.setPoints(initiator.getPoints() + 3);
        } else if (joinerScore > initiatorScore) {
            // Joiner wins => +3 points
            winnerId = joiner.getId();
            joiner.setPoints(joiner.getPoints() + 3);
        } else {
            // It's a tie => each gets +1 point
            initiator.setPoints(initiator.getPoints() + 1);
            joiner.setPoints(joiner.getPoints() + 1);
        }

        // 4) Save both updated participations
        participationRepository.save(initiator);
        participationRepository.save(joiner);

        // 5) Broadcast "GAME_COMPLETED" event with the winner (or null if tie)
        gameNotificationHub.broadcastGameCompleted(game, winnerId);
    }


}
