package nert.javaguides.sprintboot.hubs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class QuizNotificationHub {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public QuizNotificationHub(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyEventOpened(Long quizId) {
        String destination = "/topic/quizzes/" + quizId; // Target specific topic
        messagingTemplate.convertAndSend(destination, "quiz_opened");
    }
}