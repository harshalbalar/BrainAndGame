package nert.javaguides.sprintboot.model;

import jakarta.persistence.*;

@Embeddable
public class Question {

    private String text; // The question text

    public Question() {}

    public Question(String text) {
        this.text = text;
    }

    // Getters and setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
