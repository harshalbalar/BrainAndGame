package nert.javaguides.sprintboot.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import nert.javaguides.sprintboot.model.event.Event;
import nert.javaguides.sprintboot.model.event.EventKey;
import nert.javaguides.sprintboot.model.participation.Participation;
import nert.javaguides.sprintboot.model.participation.ParticipationKey;
import nert.javaguides.sprintboot.model.quiz.Quiz;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String role;

    private String linkedInLink;

    private String instagramLink;


    // One User can have many Own Events (created by the user)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    // One User can have many Own Events (created by the user)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Quiz> quizzes = new HashSet<>();

    // One User can have many Participations
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Participation> participations = new HashSet<>();



    @JsonProperty("ownEventKeys")
    public Set<EventKey> getOwnEventKeys() {
        if (this.events == null) {
            return null;
        }
        return events.stream()
                .map(event -> event.getEventKey())
                .collect(Collectors.toSet());
    }

    @JsonProperty("participationKeys")
    public Set<ParticipationKey> getParticipationKeys() {
        if (this.participations == null) {
            return null;
        }
        return participations.stream()
                .map(participation -> participation.getParticipationKey())
                .collect(Collectors.toSet());
    }

    // Default constructor
    public User() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }

    public Set<Participation> getParticipations() {
        return participations;
    }

    public void addParticipation(Participation participation) {
        participations.add(participation);
        participation.setUser(this);
    }

    public void removeParticipation(Participation participation) {
        participations.remove(participation);
        participation.setUser(null);
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setOwnEvents(Set<Event> events) {
        this.events = events;
    }

    public void setParticipations(Set<Participation> participations) {
        this.participations = participations;
    }

    @JsonIgnore
    public UserKey getUserKey(){
        return new UserKey(
                this.id,
                this.username,
                this.linkedInLink,
                this.instagramLink,
                this.role
        );
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}