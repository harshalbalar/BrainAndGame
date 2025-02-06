package nert.javaguides.sprintboot.controller;

import nert.javaguides.sprintboot.model.question.Question;
import nert.javaguides.sprintboot.service.QuestionService;
import nert.javaguides.sprintboot.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// Import other necessary packages
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionController {

    private final QuestionService questionService;
    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    public QuestionController(QuestionService questionService, JwtUtil jwtUtil) {
        this.questionService = questionService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Creates a new question for a specific quiz.
     *
     * @param quizId     The ID of the quiz.
     * @param question   The question to be created.
     * @param authHeader The Authorization header containing the JWT token.
     * @return The created question.
     */
    @PostMapping("/quiz/{quizId}")
    public ResponseEntity<Question> createQuestion(
            @PathVariable Long quizId,
            @RequestBody Question question,
            @RequestHeader("Authorization") String authHeader) throws Exception {

        // Optional: Verify if the authenticated user is authorized to add questions to the quiz

        Question createdQuestion = questionService.createQuestion(quizId, question);
        return ResponseEntity.ok(createdQuestion);
    }

    /**
     * Retrieves all questions for a specific quiz.
     *
     * @param quizId The ID of the quiz.
     * @return A list of questions.
     */
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Question>> getQuestionsByQuizId(@PathVariable Long quizId) throws Exception {
        List<Question> questions = questionService.getQuestionsByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }

    /**
     * Updates an existing question.
     *
     * @param questionId    The ID of the question to update.
     * @param updatedQuestion The updated question details.
     * @param authHeader    The Authorization header containing the JWT token.
     * @return The updated question.
     */
    @PutMapping("/{questionId}")
    public ResponseEntity<Question> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody Question updatedQuestion,
            @RequestHeader("Authorization") String authHeader) throws Exception {

        // Optional: Verify if the authenticated user is authorized to update the question

        Question question = questionService.updateQuestion(questionId, updatedQuestion);
        return ResponseEntity.ok(question);
    }

    /**
     * Deletes a question by its ID.
     *
     * @param questionId The ID of the question to delete.
     * @param authHeader The Authorization header containing the JWT token.
     * @return A success message.
     */
    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> deleteQuestion(
            @PathVariable Long questionId,
            @RequestHeader("Authorization") String authHeader) throws Exception {

        // Optional: Verify if the authenticated user is authorized to delete the question

        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok("Question successfully deleted");
    }
}
