package nert.javaguides.sprintboot.model.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import nert.javaguides.sprintboot.model.question.Question;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private boolean correct;

    // Many Answers belong to one Question
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

    // Constructors
    public Answer() {}

    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}

