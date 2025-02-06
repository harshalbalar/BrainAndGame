package nert.javaguides.sprintboot.config;

import jakarta.annotation.PostConstruct;
import nert.javaguides.sprintboot.model.game.QuickGameQuestion;
import nert.javaguides.sprintboot.service.QuickGameQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionInitializer {

    private final QuickGameQuestionService questionService;

    @Autowired
    public QuestionInitializer(QuickGameQuestionService questionService) {
        this.questionService = questionService;
    }

    @PostConstruct
    public void initializeQuestions() {
        List<QuickGameQuestion> initialQuestions = List.of(
                new QuickGameQuestion("What is 2+2?", List.of("3", "4", "5"), "4", "Math", "Easy"),
                new QuickGameQuestion("What is the capital of France?", List.of("Paris", "Rome", "Berlin"), "Paris", "Geography", "Medium"),
                new QuickGameQuestion("Who wrote 'Hamlet'?", List.of("Shakespeare", "Hemingway", "Orwell"), "Shakespeare", "Literature", "Hard"),
                new QuickGameQuestion("What is the chemical symbol for water?", List.of("H2O", "CO2", "NaCl"), "H2O", "Science", "Easy"),
                new QuickGameQuestion("Who painted the Mona Lisa?", List.of("Michelangelo", "Leonardo da Vinci", "Van Gogh"), "Leonardo da Vinci", "Art", "Medium"),
                new QuickGameQuestion("What is the square root of 81?", List.of("7", "8", "9"), "9", "Math", "Easy"),
                new QuickGameQuestion("Which planet is known as the Red Planet?", List.of("Earth", "Mars", "Venus"), "Mars", "Science", "Easy"),
                new QuickGameQuestion("What is the largest ocean on Earth?", List.of("Atlantic", "Indian", "Pacific"), "Pacific", "Geography", "Medium"),
                new QuickGameQuestion("Who discovered gravity?", List.of("Newton", "Einstein", "Galileo"), "Newton", "Science", "Easy"),
                new QuickGameQuestion("What year did World War II end?", List.of("1945", "1939", "1941"), "1945", "History", "Medium"),
                new QuickGameQuestion("What is the fastest land animal?", List.of("Cheetah", "Lion", "Horse"), "Cheetah", "Nature", "Easy"),
                new QuickGameQuestion("What is the boiling point of water in Celsius?", List.of("90°C", "100°C", "110°C"), "100°C", "Science", "Easy"),
                new QuickGameQuestion("Which is the largest continent by area?", List.of("Africa", "Asia", "Europe"), "Asia", "Geography", "Medium"),
                new QuickGameQuestion("Who developed the theory of relativity?", List.of("Newton", "Einstein", "Bohr"), "Einstein", "Science", "Hard"),
                new QuickGameQuestion("What is the capital of Japan?", List.of("Beijing", "Seoul", "Tokyo"), "Tokyo", "Geography", "Medium"),
                new QuickGameQuestion("Who wrote the Harry Potter series?", List.of("J.R.R. Tolkien", "J.K. Rowling", "George R.R. Martin"), "J.K. Rowling", "Literature", "Easy"),
                new QuickGameQuestion("What is the hardest natural substance on Earth?", List.of("Gold", "Iron", "Diamond"), "Diamond", "Science", "Easy"),
                new QuickGameQuestion("What is the smallest prime number?", List.of("1", "2", "3"), "2", "Math", "Easy"),
                new QuickGameQuestion("Which country is known as the Land of the Rising Sun?", List.of("China", "Japan", "India"), "Japan", "Geography", "Easy"),
                new QuickGameQuestion("What is the primary gas found in Earth's atmosphere?", List.of("Oxygen", "Nitrogen", "Carbon Dioxide"), "Nitrogen", "Science", "Medium")
        );

        questionService.initializeQuestions(initialQuestions);
    }
}
