package nert.javaguides.sprintboot.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Quiz> quizzes;

    // Set isOpen to false by default
    private boolean isOpen = false;

    // Field to store the ID of the user who created the event
    private Long userId;

    // Constructors, Getters and Setters
    public Event() {}

    public Event(String name, List<Quiz> quizzes, Long userId) {
        this.name = name;
        this.quizzes = quizzes;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Event{id=" + id +
                ", name='" + name + '\'' +
                ", isOpen=" + isOpen +
                ", userId=" + userId +
                ", quizzes=" + quizzes + '}';
    }
}
