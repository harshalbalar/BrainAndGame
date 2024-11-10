package nert.javaguides.sprintboot.controller;

import nert.javaguides.sprintboot.model.Quiz;
import nert.javaguides.sprintboot.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<String> createQuiz(@RequestBody Quiz quiz) {
        quizService.saveQuiz(quiz);
        return ResponseEntity.ok("Quiz successfully saved");
    }

    // New endpoint to get all quizzes
    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }
}