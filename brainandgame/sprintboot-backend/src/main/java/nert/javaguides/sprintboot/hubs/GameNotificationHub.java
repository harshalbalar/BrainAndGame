package nert.javaguides.sprintboot.hubs;

import nert.javaguides.sprintboot.model.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class GameNotificationHub {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Broadcast a "NEW_GAME" message to both participants (initiator & joiner).
     */
    public void broadcastNewGame(Game game) {
        Long initiatorId = game.getInitiator().getId();
        Long joinerId = game.getJoiner().getId();

        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "NEW_GAME");
        payload.put("gameId", game.getId());

        messagingTemplate.convertAndSend("/topic/games/" + initiatorId, payload);
        messagingTemplate.convertAndSend("/topic/games/" + joinerId, payload);
    }

    /**
     * Broadcast a "GAME_COMPLETED" message to both participants.
     */
    public void broadcastGameCompleted(Game game, Long winnerId) {
        Long initiatorId = game.getInitiator().getId();
        Long joinerId = game.getJoiner().getId();

        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "GAME_COMPLETED");
        payload.put("gameId", game.getId());
        payload.put("winnerId", winnerId); // or handle ties differently

        messagingTemplate.convertAndSend("/topic/games/" + initiatorId, payload);
        messagingTemplate.convertAndSend("/topic/games/" + joinerId, payload);
    }

    public void broadcastGameAccepted(Game game) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "GAME_ACCEPTED");
        payload.put("gameId", game.getId());
        messagingTemplate.convertAndSend("/topic/games/" + game.getInitiator().getId(), payload);
        messagingTemplate.convertAndSend("/topic/games/" + game.getJoiner().getId(), payload);
    }
}
