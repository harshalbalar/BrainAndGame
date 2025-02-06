package nert.javaguides.sprintboot.model.participation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import nert.javaguides.sprintboot.model.event.Event;
import nert.javaguides.sprintboot.model.event.EventKey;
import nert.javaguides.sprintboot.model.user.User;
import nert.javaguides.sprintboot.model.user.UserKey;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "participations",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "event_id"})})
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Integer tickets = 5;


    private Boolean quizPlayed = false;

    private Integer points = 0;

    // Many Participations belong to one User
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @JsonProperty("userKey")
    public UserKey getUserKey() {
        if (this.user == null) {
            return null;
        }
        return user.getUserKey();
    }

    // Many Participations belong to one Event
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    @JsonIgnore
    private Event event;
    // Additional fields can be added here (e.g., participationDate)

    @JsonProperty("eventKey")
    public EventKey getEventKey() {
        if (this.event == null) {
            return null;
        }
        return event.getEventKey();
    }

    // Constructors
    public Participation() {}

    public Participation(User user, Event event) {
        this.user = user;
        this.event = event;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    // No setter for ID as it's auto-generated

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getTickets() {
        return tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getQuizPlayed() {
        return quizPlayed;
    }

    public void setQuizPlayed(Boolean quizPlayed) {
        this.quizPlayed = quizPlayed;
    }

    @JsonIgnore
    public ParticipationKey getParticipationKey() {
        return new ParticipationKey(
                this.id,
                this.tickets,
                this.points,
                this.user.getUserKey(),
                this.event.getEventKey()
        );
    }


    // Optionally, override equals() and hashCode() based on user and event to prevent duplicates
}

