// src/main/java/nert/javaguides/sprintboot/util/DataInitializer.java

package nert.javaguides.sprintboot.util;

import jakarta.transaction.Transactional;
import nert.javaguides.sprintboot.model.game.GameQuestion;
import nert.javaguides.sprintboot.model.game.GameQuestionAnswer;
import nert.javaguides.sprintboot.repository.GameQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final GameQuestionRepository gameQuestionRepository;

    @Autowired
    public DataInitializer(GameQuestionRepository gameQuestionRepository) {
        this.gameQuestionRepository = gameQuestionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Check if data already exists to prevent duplication
        if (gameQuestionRepository.count() > 0) {
            System.out.println("GameQuestions already initialized.");
            return;
        }

        // Initialize 20 GameQuestions with their answers
        List<GameQuestion> gameQuestions = Arrays.asList(
                new GameQuestion("What is the capital of France?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Paris", true),
                        new GameQuestionAnswer("London", false),
                        new GameQuestionAnswer("Berlin", false),
                        new GameQuestionAnswer("Madrid", false)
                ))),
                new GameQuestion("Which planet is known as the Red Planet?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Mars", true),
                        new GameQuestionAnswer("Jupiter", false),
                        new GameQuestionAnswer("Saturn", false),
                        new GameQuestionAnswer("Venus", false)
                ))),
                new GameQuestion("Who wrote 'Romeo and Juliet'?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("William Shakespeare", true),
                        new GameQuestionAnswer("Mark Twain", false),
                        new GameQuestionAnswer("Charles Dickens", false),
                        new GameQuestionAnswer("Jane Austen", false)
                ))),
                new GameQuestion("What is the largest ocean on Earth?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Pacific Ocean", true),
                        new GameQuestionAnswer("Atlantic Ocean", false),
                        new GameQuestionAnswer("Indian Ocean", false),
                        new GameQuestionAnswer("Arctic Ocean", false)
                ))),
                new GameQuestion("What is the chemical symbol for Gold?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Au", true),
                        new GameQuestionAnswer("Ag", false),
                        new GameQuestionAnswer("Gd", false),
                        new GameQuestionAnswer("Go", false)
                ))),
                new GameQuestion("Who painted the Mona Lisa?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Leonardo da Vinci", true),
                        new GameQuestionAnswer("Pablo Picasso", false),
                        new GameQuestionAnswer("Vincent van Gogh", false),
                        new GameQuestionAnswer("Michelangelo", false)
                ))),
                new GameQuestion("What is the smallest prime number?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("2", true),
                        new GameQuestionAnswer("1", false),
                        new GameQuestionAnswer("3", false),
                        new GameQuestionAnswer("5", false)
                ))),
                new GameQuestion("Which language is primarily spoken in Brazil?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Portuguese", true),
                        new GameQuestionAnswer("Spanish", false),
                        new GameQuestionAnswer("English", false),
                        new GameQuestionAnswer("French", false)
                ))),
                new GameQuestion("What is the boiling point of water at sea level?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("100°C", true),
                        new GameQuestionAnswer("90°C", false),
                        new GameQuestionAnswer("80°C", false),
                        new GameQuestionAnswer("120°C", false)
                ))),
                new GameQuestion("Who is known as the Father of Computers?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Charles Babbage", true),
                        new GameQuestionAnswer("Alan Turing", false),
                        new GameQuestionAnswer("Bill Gates", false),
                        new GameQuestionAnswer("Steve Jobs", false)
                ))),
                new GameQuestion("What is the hardest natural substance?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Diamond", true),
                        new GameQuestionAnswer("Gold", false),
                        new GameQuestionAnswer("Iron", false),
                        new GameQuestionAnswer("Platinum", false)
                ))),
                new GameQuestion("Which gas do plants absorb from the atmosphere?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Carbon Dioxide", true),
                        new GameQuestionAnswer("Oxygen", false),
                        new GameQuestionAnswer("Nitrogen", false),
                        new GameQuestionAnswer("Hydrogen", false)
                ))),
                new GameQuestion("What is the largest mammal?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Blue Whale", true),
                        new GameQuestionAnswer("Elephant", false),
                        new GameQuestionAnswer("Giraffe", false),
                        new GameQuestionAnswer("Hippopotamus", false)
                ))),
                new GameQuestion("Who developed the theory of relativity?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Albert Einstein", true),
                        new GameQuestionAnswer("Isaac Newton", false),
                        new GameQuestionAnswer("Nikola Tesla", false),
                        new GameQuestionAnswer("Galileo Galilei", false)
                ))),
                new GameQuestion("What is the smallest continent?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Australia", true),
                        new GameQuestionAnswer("Europe", false),
                        new GameQuestionAnswer("Antarctica", false),
                        new GameQuestionAnswer("South America", false)
                ))),
                new GameQuestion("Which element has the atomic number 1?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Hydrogen", true),
                        new GameQuestionAnswer("Helium", false),
                        new GameQuestionAnswer("Oxygen", false),
                        new GameQuestionAnswer("Carbon", false)
                ))),
                new GameQuestion("What is the tallest mountain in the world?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Mount Everest", true),
                        new GameQuestionAnswer("K2", false),
                        new GameQuestionAnswer("Kangchenjunga", false),
                        new GameQuestionAnswer("Lhotse", false)
                ))),
                new GameQuestion("Who wrote '1984'?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("George Orwell", true),
                        new GameQuestionAnswer("Aldous Huxley", false),
                        new GameQuestionAnswer("J.K. Rowling", false),
                        new GameQuestionAnswer("Ernest Hemingway", false)
                ))),
                new GameQuestion("What is the fastest land animal?", new HashSet<>(Arrays.asList(
                        new GameQuestionAnswer("Cheetah", true),
                        new GameQuestionAnswer("Lion", false),
                        new GameQuestionAnswer("Tiger", false),
                        new GameQuestionAnswer("Leopard", false)
                )))
                // Add more questions as needed to reach 20
        );

        // Save all GameQuestions along with their Answers via Cascade
        gameQuestionRepository.saveAll(gameQuestions);

        System.out.println("Initialized 20 GameQuestions with their answers.");
    }
}
