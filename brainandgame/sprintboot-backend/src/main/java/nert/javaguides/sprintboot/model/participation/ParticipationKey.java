package nert.javaguides.sprintboot.model.participation;

import nert.javaguides.sprintboot.model.event.EventKey;
import nert.javaguides.sprintboot.model.user.UserKey;

public class ParticipationKey {

    private Long id;
    private Integer tickets;
    private Integer points;
    private UserKey userKey;
    private EventKey eventKey;


    public ParticipationKey(Long id, Integer tickets, Integer points, UserKey userKey, EventKey eventKey) {
        this.id = id;
        this.tickets = tickets;
        this.points = points;
        this.userKey = userKey;
        this.eventKey = eventKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserKey getUserKey() {
        return userKey;
    }

    public void setUserKey(UserKey userKey) {
        this.userKey = userKey;
    }

    public EventKey getEventKey() {
        return eventKey;
    }

    public void setEventKey(EventKey eventKey) {
        this.eventKey = eventKey;
    }
}
