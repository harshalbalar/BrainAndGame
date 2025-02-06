package nert.javaguides.sprintboot.service;

import nert.javaguides.sprintboot.model.question.Question;
import nert.javaguides.sprintboot.model.quiz.Quiz;
import nert.javaguides.sprintboot.model.user.User;
import nert.javaguides.sprintboot.repository.QuizRepository;
import nert.javaguides.sprintboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private final QuizRepository quizRepository;

    @Autowired
    private final UserRepository userRepository;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    // Method to retrieve all quizzes
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz addQuestionToQuiz(Long quizId, Question question) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.getQuestions().add(question);
        return quizRepository.save(quiz);
    }

    public Optional<Quiz> getQuizById(Long quizId) {
        return quizRepository.findById(quizId);
    }

    // Get "my quizzes" - quizzes created by the user
    public List<Quiz> getMyQuizzes(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id " + userId));

        return quizRepository.findAllByUserId(user.getId());
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