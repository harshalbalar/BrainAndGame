// package nert.javaguides.sprintboot.service;

package nert.javaguides.sprintboot.service;

import nert.javaguides.sprintboot.model.game.GameQuestionAnswer;
import nert.javaguides.sprintboot.repository.GameQuestionAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GameQuestionAnswerService {

    private final GameQuestionAnswerRepository gameQuestionAnswerRepository;

    @Autowired
    public GameQuestionAnswerService(GameQuestionAnswerRepository gameQuestionAnswerRepository) {
        this.gameQuestionAnswerRepository = gameQuestionAnswerRepository;
    }

    // Add or update a game question answer
    @Transactional
    public GameQuestionAnswer saveAnswer(GameQuestionAnswer answer) {
        return gameQuestionAnswerRepository.save(answer);
    }

    // Fetch answer by ID
    public Optional<GameQuestionAnswer> getAnswerById(Long answerId) {
        return gameQuestionAnswerRepository.findById(answerId);
    }

    // Additional methods as needed
}
