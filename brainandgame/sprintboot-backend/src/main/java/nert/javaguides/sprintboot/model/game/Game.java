// package nert.javaguides.sprintboot.model.game;

package nert.javaguides.sprintboot.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import nert.javaguides.sprintboot.model.participation.Participation;
import nert.javaguides.sprintboot.model.user.UserKey;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Initiator of the game
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "initiator_id", nullable = false)
    @JsonIgnore
    private Participation initiator;

    @JsonProperty("initiatorUserKey")
    public UserKey getInitiatorUserKey() {
        return (initiator != null) ? initiator.getUserKey() : null;
    }


    // Joiner of the game
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "joiner_id", nullable = false)
    @JsonIgnore
    private Participation joiner;

    // Example convenience getters for the front end:
    @JsonProperty("initiatorParticipationId")
    public Long getInitiatorParticipationId() {
        return (initiator != null) ? initiator.getId() : null;
    }

    @JsonProperty("joinerParticipationId")
    public Long getJoinerParticipationId() {
        return (joiner != null) ? joiner.getId() : null;
    }

    @JsonProperty("joinerUserKey")
    public UserKey getJoinerUserKey() {
        return (joiner != null) ? joiner.getUserKey() : null;
    }

    // Many-to-Many relationship with GameQuestion
    @ManyToMany
    @JoinTable(
            name = "game_game_questions",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "game_question_id")
    )
    @JsonProperty("gameQuestions")
    private List<GameQuestion> gameQuestions = new ArrayList<>();

    // Scores
    private Integer initiatorScore = 0;
    private Integer joinerScore = 0;

    // Keep track of how many questions each participant has answered.
    private Integer initiatorAnswers = 0;
    private Integer joinerAnswers = 0;


    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    // Status (e.g., "PENDING", "ONGOING", "COMPLETED")
    private String status = "PENDING";

    // Constructors

    public Game() {
        this.createdAt = LocalDateTime.now();
    }

    public Game(Participation initiator, Participation joiner) {
        this.initiator = initiator;
        this.joiner = joiner;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Participation getInitiator() {
        return initiator;
    }

    public void setInitiator(Participation initiator) {
        this.initiator = initiator;
    }

    public Participation getJoiner() {
        return joiner;
    }

    public void setJoiner(Participation joiner) {
        this.joiner = joiner;
    }

    public List<GameQuestion> getGameQuestions() {
        return gameQuestions;
    }

    public void setGameQuestions(List<GameQuestion> gameQuestions) {
        this.gameQuestions = gameQuestions;
    }

    public Integer getInitiatorScore() {
        return initiatorScore;
    }

    public void setInitiatorScore(Integer initiatorScore) {
        this.initiatorScore = initiatorScore;
    }

    public Integer getJoinerScore() {
        return joinerScore;
    }

    public void setJoinerScore(Integer joinerScore) {
        this.joinerScore = joinerScore;
    }

    public Integer getInitiatorAnswers() {
        return initiatorAnswers;
    }

    public void setInitiatorAnswers(Integer initiatorAnswers) {
        this.initiatorAnswers = initiatorAnswers;
    }

    public Integer getJoinerAnswers() {
        return joinerAnswers;
    }

    public void setJoinerAnswers(Integer joinerAnswers) {
        this.joinerAnswers = joinerAnswers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

