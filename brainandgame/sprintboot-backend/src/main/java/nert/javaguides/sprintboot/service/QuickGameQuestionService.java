package nert.javaguides.sprintboot.service;

import nert.javaguides.sprintboot.model.game.QuickGameQuestion;
import nert.javaguides.sprintboot.repository.QuickGameQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuickGameQuestionService {

    private final QuickGameQuestionRepository questionRepository;

    @Autowired
    public QuickGameQuestionService(QuickGameQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuickGameQuestion> getAllQuestions() {
        return questionRepository.findAll();
    }

    public QuickGameQuestion saveQuestion(QuickGameQuestion question) {
        return questionRepository.save(question);
    }

    public void initializeQuestions(List<QuickGameQuestion> initialQuestions) {
        questionRepository.saveAll(initialQuestions);
    }
}
