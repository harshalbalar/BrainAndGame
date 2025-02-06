// src/main/java/nert/javaguides/sprintboot/model/game/GameQuestion.java

package nert.javaguides.sprintboot.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "game_questions")
public class GameQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Question text
    private String questionText;

    // Set of possible answers
    @OneToMany(mappedBy = "gameQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("gameQuestionAnswers")
    private Set<GameQuestionAnswer> gameQuestionAnswers = new HashSet<>();

    // Constructors

    public GameQuestion() {}

    public GameQuestion(String questionText, Set<GameQuestionAnswer> gameQuestionAnswers) {
        this.questionText = questionText;
        this.setGameQuestionAnswers(gameQuestionAnswers);
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Set<GameQuestionAnswer> getGameQuestionAnswers() {
        return gameQuestionAnswers;
    }

    public void setGameQuestionAnswers(Set<GameQuestionAnswer> gameQuestionAnswers) {
        this.gameQuestionAnswers = gameQuestionAnswers;
        for (GameQuestionAnswer answer : gameQuestionAnswers) {
            answer.setGameQuestion(this);
        }
    }

    // Helper Methods

    public void addGameQuestionAnswer(GameQuestionAnswer answer) {
        this.gameQuestionAnswers.add(answer);
        answer.setGameQuestion(this);
    }

    public void removeGameQuestionAnswer(GameQuestionAnswer answer) {
        this.gameQuestionAnswers.remove(answer);
        answer.setGameQuestion(null);
    }
}
