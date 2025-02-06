package nert.javaguides.sprintboot.service;
import nert.javaguides.sprintboot.model.question.Question;
import nert.javaguides.sprintboot.model.quiz.Quiz;
import nert.javaguides.sprintboot.repository.QuestionRepository;
import nert.javaguides.sprintboot.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }

    /**
     * Creates a new question associated with a specific quiz.
     *
     * @param quizId  The ID of the quiz.
     * @param question The question to be created.
     * @return The created question.
     */
    public Question createQuestion(Long quizId, Question question) throws Exception {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new Exception("Quiz not found with id " + quizId));
        question.setQuiz(quiz);
        // Ensure that each answer is associated with the question
        if (question.getAnswers() != null) {
            question.getAnswers().forEach(answer -> answer.setQuestion(question));
        }
        return questionRepository.save(question);
    }

    /**
     * Retrieves all questions for a specific quiz.
     *
     * @param quizId The ID of the quiz.
     * @return A list of questions.
     */
    public List<Question> getQuestionsByQuizId(Long quizId) throws Exception {
        if (!quizRepository.existsById(quizId)) {
            throw new Exception("Quiz not found with id " + quizId);
        }
        return questionRepository.findAllByQuizId(quizId);
    }

    /**
     * Updates an existing question.
     *
     * @param questionId      The ID of the question to update.
     * @param updatedQuestion The updated question details.
     * @return The updated question.
     */
    public Question updateQuestion(Long questionId, Question updatedQuestion) throws Exception {
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new Exception("Question not found with id " + questionId));
        existingQuestion.setText(updatedQuestion.getText());
        // Update other fields or manage answers as needed
        return questionRepository.save(existingQuestion);
    }

    /**
     * Deletes a question by its ID.
     *
     * @param questionId The ID of the question to delete.
     */
    public void deleteQuestion(Long questionId) throws Exception {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new Exception("Question not found with id " + questionId));
        questionRepository.delete(question);
    }
}
