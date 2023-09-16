package in.watch.rooms.controller;


import in.watch.rooms.dto.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    @MessageMapping("/message")//client will send message on this =>/app/message
    @SendTo("/topic/return-to")
//any client subscribed to this url will receive the data return by this method.__(/topic is used to broadcast its in config)
    public Message getContent(@Payload Message message) {

    }




/*
//if you want to add data in socket session
    @MessageMapping("/adduser")
    @SendTo("/topic/adduser")

    public Message getContent(@Payload Message message, SimpMessageHeaderAccessor accessor) {
        accessor.getSessionAttributes().put("user","set user from payload");

    }
}*/
