// package nert.javaguides.sprintboot.service;

package nert.javaguides.sprintboot.service;

import nert.javaguides.sprintboot.model.game.GameQuestion;
import nert.javaguides.sprintboot.model.game.GameQuestionAnswer;
import nert.javaguides.sprintboot.repository.GameQuestionRepository;
import nert.javaguides.sprintboot.repository.GameQuestionAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GameQuestionService {

    private final GameQuestionRepository gameQuestionRepository;
    private final GameQuestionAnswerRepository gameQuestionAnswerRepository;

    @Autowired
    public GameQuestionService(GameQuestionRepository gameQuestionRepository,
                               GameQuestionAnswerRepository gameQuestionAnswerRepository) {
        this.gameQuestionRepository = gameQuestionRepository;
        this.gameQuestionAnswerRepository = gameQuestionAnswerRepository;
    }

    // Add a new question to a game
    @Transactional
    public GameQuestion addQuestion(GameQuestion gameQuestion, List<GameQuestionAnswer> answers) {
        GameQuestion savedQuestion = gameQuestionRepository.save(gameQuestion);
        for (GameQuestionAnswer answer : answers) {
            answer.setGameQuestion(savedQuestion);
            gameQuestionAnswerRepository.save(answer);
            savedQuestion.getGameQuestionAnswers().add(answer);
        }
        return savedQuestion;
    }

    // Fetch question by ID
    public Optional<GameQuestion> getQuestionById(Long questionId) {
        return gameQuestionRepository.findById(questionId);
    }

    // Additional methods as needed
}
