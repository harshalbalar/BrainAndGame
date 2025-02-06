package nert.javaguides.sprintboot.model.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import nert.javaguides.sprintboot.model.participation.Participation;
import nert.javaguides.sprintboot.model.participation.ParticipationKey;
import nert.javaguides.sprintboot.model.quiz.Quiz;
import nert.javaguides.sprintboot.model.quiz.QuizKey;
import nert.javaguides.sprintboot.model.user.User;
import nert.javaguides.sprintboot.model.user.UserKey;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Many Events belong to one Quiz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;

    @JsonProperty("quizKey")
    public QuizKey getQuizKey() {
        if (this.quiz == null) {
            return null;
        }
        return quiz.getQuizKey();
    }

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

    // One Event can have many Participations
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Participation> participations = new HashSet<>();

    @JsonProperty("participationKeys")
    public Set<ParticipationKey> getParticipationKeys() {
        if (this.participations == null) {
            return null;
        }
        return participations.stream()
                .map(participation -> participation.getParticipationKey())
                .collect(Collectors.toSet());
    }

    // Set isOpen to false by default
    private boolean open = false;

    // Constructors, Getters and Setters
    public Event() {}

    public Event(String name) {
        this.name = name;

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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Set<Participation> getParticipations() {
        return participations;
    }

    public void addParticipation(Participation participation) {
        participations.add(participation);
        participation.setEvent(this);
    }

    public void removeParticipation(Participation participation) {
        participations.remove(participation);
        participation.setEvent(null);
    }

    @JsonIgnore
    public EventKey getEventKey(){
        return new EventKey(
                this.id,
                this.name,
                this.open
        );
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setParticipations(Set<Participation> participations) {
        this.participations = participations;
    }
}
