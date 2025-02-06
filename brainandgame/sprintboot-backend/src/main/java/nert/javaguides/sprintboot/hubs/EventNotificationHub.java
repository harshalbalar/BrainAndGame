package nert.javaguides.sprintboot.hubs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventNotificationHub {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public EventNotificationHub(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyEventOpened(Long eventId) {
        String destination = "/topic/events/" + eventId; // Target specific topic
        messagingTemplate.convertAndSend(destination, "event_opened");
    }
}
