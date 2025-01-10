package nert.javaguides.sprintboot.service;

import nert.javaguides.sprintboot.model.Quiz;
import nert.javaguides.sprintboot.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    // Method to retrieve all quizzes
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz addQuestionToQuiz(Long quizId, String question) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.getQuestions().add(question);
        return quizRepository.save(quiz);
    }

    public Quiz getQuizById(Long quizId) {
        return null;
    }

    // Toggle quiz status (open/close)
    public Quiz toggleQuizStatus(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Toggle the status
        quiz.setOpen(!quiz.isOpen());

        // Save and return the updated quiz
        return quizRepository.save(quiz);
    }
}