// src/main/java/nert/javaguides/sprintboot/model/game/GameQuestionAnswer.java

package nert.javaguides.sprintboot.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "game_question_answers")
public class GameQuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many GameQuestionAnswers belong to one GameQuestion
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_question_id", nullable = false)
    @JsonIgnore
    private GameQuestion gameQuestion;

    // Answer text
    private String answerText;

    // Indicates whether the answer is correct
    private Boolean isCorrect = false;

    // Constructors

    public GameQuestionAnswer() {}

    public GameQuestionAnswer(String answerText, Boolean isCorrect) {
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public GameQuestion getGameQuestion() {
        return gameQuestion;
    }

    public void setGameQuestion(GameQuestion gameQuestion) {
        this.gameQuestion = gameQuestion;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
