package nert.javaguides.sprintboot.controller;

import nert.javaguides.sprintboot.model.game.QuickGameQuestion;
import nert.javaguides.sprintboot.service.QuickGameQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuickGameQuestionController {

    private final QuickGameQuestionService questionService;

    @Autowired
    public QuickGameQuestionController(QuickGameQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<QuickGameQuestion> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PostMapping
    public QuickGameQuestion addQuestion(@RequestBody QuickGameQuestion question) {
        return questionService.saveQuestion(question);
    }

    @PostMapping("/initialize")
    public void initializeQuestions(@RequestBody List<QuickGameQuestion> questions) {
        questionService.initializeQuestions(questions);
    }
}
