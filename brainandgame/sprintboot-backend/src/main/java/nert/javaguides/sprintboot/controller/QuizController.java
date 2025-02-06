package nert.javaguides.sprintboot.controller;

import nert.javaguides.sprintboot.hubs.QuizNotificationHub;
import nert.javaguides.sprintboot.model.question.Question;
import nert.javaguides.sprintboot.model.quiz.Quiz;
import nert.javaguides.sprintboot.model.user.User;
import nert.javaguides.sprintboot.repository.UserRepository;
import nert.javaguides.sprintboot.service.QuizService;
import nert.javaguides.sprintboot.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

    private final QuizService quizService;
    private final UserRepository userRepository;
    private final QuizNotificationHub quizNotificationHub;
    private final JwtUtil jwtUtil;

    @Autowired
    public QuizController(QuizService quizService, UserRepository userRepository, QuizNotificationHub quizNotificationHub, JwtUtil jwtUtil) {
        this.quizService = quizService;
        this.userRepository = userRepository;
        this.quizNotificationHub = quizNotificationHub;

        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<String> createQuiz(
            @RequestBody Quiz quiz,
            @RequestHeader("Authorization") String authHeader
    ) {
        // 1. Extract the token from the Authorization header
        String token = authHeader.replace("Bearer ", "").trim();

        // 2. Extract the userId from the token using JwtUtil
        Long userId = jwtUtil.extractUserId(token);

        // 2) Fetch the User from the DB
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userOpt.get();

        quiz.setUser(user);

        quizService.saveQuiz(quiz);
        return ResponseEntity.ok("Quiz successfully saved");
    }


    // New endpoint to get all quizzes
    @GetMapping
    public ResponseEntity<Quiz> getQuiz(@RequestParam Long quizId) {
        Optional<Quiz> quiz = quizService.getQuizById(quizId);

        if (quiz.isPresent()) {
            return ResponseEntity.ok(quiz.get());
        }
        return ResponseEntity.notFound().build();

    }

    // Get "my quizzes" - quizzes created by the authenticated user
    @GetMapping("/my")
    public ResponseEntity<List<Quiz>> getMyQuizzes(@RequestHeader("Authorization") String authHeader) throws Exception {
        // 1. Extract the token from the Authorization header
        String token = authHeader.replace("Bearer ", "").trim();

        // 2. Extract the userId from the token using JwtUtil
        Long userId = jwtUtil.extractUserId(token);

        // 2) Fetch the User from the DB
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userOpt.get();

        // 3. Retrieve quizzes created by the user
        List<Quiz> myQuizzes = quizService.getMyQuizzes(userId);

        return ResponseEntity.ok(myQuizzes);
    }

    @PostMapping("/{quizId}/questions")
    public ResponseEntity<String> addQuestion(@PathVariable Long quizId, @RequestBody Question question) {
        quizService.addQuestionToQuiz(quizId, question);
        return ResponseEntity.ok("Question successfully added");
    }

    // Endpoint to toggle quiz status (open/closed)
    @PutMapping("/{quizId}/toggle-status")
    public ResponseEntity<String> toggleQuizStatus(@PathVariable Long quizId) {
        Quiz quiz = quizService.toggleQuizStatus(quizId);
        if(quiz.isOpen()){
            quizNotificationHub.notifyEventOpened(quizId);
        }
        return ResponseEntity.ok("Quiz status successfully updated");
    }
}