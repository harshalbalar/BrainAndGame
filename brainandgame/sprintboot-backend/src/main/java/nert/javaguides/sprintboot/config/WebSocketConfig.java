package nert.javaguides.sprintboot.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple in-memory broker listening on these prefixes
        config.enableSimpleBroker("/topic", "/queue");
        // Prefix for messages sent from client to server
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws/quizzes")
                .setAllowedOriginPatterns("http://localhost:3000", "http://localhost:50951")
                .withSockJS();

        registry
                .addEndpoint("/ws/games")
                .setAllowedOriginPatterns("http://localhost:3000", "http://localhost:50951")
                .withSockJS();

    }
}