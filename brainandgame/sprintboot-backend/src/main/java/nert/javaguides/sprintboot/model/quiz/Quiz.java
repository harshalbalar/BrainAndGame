package nert.javaguides.sprintboot.model.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import nert.javaguides.sprintboot.model.question.Question;
import nert.javaguides.sprintboot.model.event.Event;
import nert.javaguides.sprintboot.model.user.User;
import nert.javaguides.sprintboot.model.user.UserKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private boolean open = true;

    // Many Events belong to one Quiz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @JsonProperty("userKey")
    public UserKey getUserKey() {
        if (this.user == null) {
            return null;
        }
        return user.getUserKey();
    }

    // One Quiz has many Questions
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    // One Quiz has many Events
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Event> events = new ArrayList<>();

    // Constructors
    public Quiz() {}

    public Quiz(String title) {
        this.title = title;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
        question.setQuiz(this);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setQuiz(null);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
        event.setQuiz(this);
    }

    public void removeEvent(Event event) {
        events.remove(event);
        event.setQuiz(null);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public QuizKey getQuizKey(){
        return new QuizKey(
                this.id,
                this.title,
                this.description,
                this.open
        );
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

