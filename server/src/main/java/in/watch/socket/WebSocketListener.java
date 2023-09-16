package in.watch.socket;

import in.watch.rooms.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketListener {

    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    //you can handle disconnect socket connections
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        // TODO -- handle the disconnect sockets
        //this how you access the info ser in socket session
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info(username + " is disconnected");

            //send a message to inform all user that one user is disconnected
            var message = Message.builder()
                    .message(username + "left")
                    .build();
            // to send the message you have to use SimpMessageSendingOperations
            messageTemplate.convertAndSend("/topic/public");
            //u can replace /public with something else but u have to listen for it
        }
    }
}
