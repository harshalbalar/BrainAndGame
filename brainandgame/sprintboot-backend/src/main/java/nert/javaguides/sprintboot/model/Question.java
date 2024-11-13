package nert.javaguides.sprintboot.model;

import jakarta.persistence.*;
import java.util.List;

@Embeddable
public class Question {

    private String text; // The question text

    @ElementCollection
    private List<String> options; // The list of 4 answer options

    private int correctAnswerIndex; // The index of the correct answer (0, 1, 2, or 3)

    // Constructors, getters, and setters
    public Question() {}

    public Question(String text, List<String> options, int correctAnswerIndex) {
        this.text = text;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }
}
