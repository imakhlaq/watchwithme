package in.watch.rooms;


import in.watch.rooms.service.RoomService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Controller
@Slf4j
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    public void getContent(HttpServletResponse response) throws IOException {

        // Replace with the path to your video file
        Path videoPath = Paths.get("F:\\earth.mp4");

        // Open the video file as an InputStream
        InputStream videoInputStream = Files.newInputStream(videoPath, StandardOpenOption.READ);

        // Create an InputStreamSource from the InputStream
        InputStreamSource source = new InputStreamResource(videoInputStream);


        StreamingResponseBody responseBody = outputStream -> {
            log.warn("inside writeTo");
            InputStream is = source.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                log.warn("Read byte {}", bytesRead);
                outputStream.write(buffer, 0, bytesRead);
            }
            is.close();
            outputStream.flush();

        };
        messagingTemplate.convertAndSend("/topic/data", responseBody);

    }


}





/*




//if you want to add data in socket session
    @MessageMapping("/adduser")  //client will send message on this =>/app/message
    @SendTo("/topic/adduser")
    //any client subscribed to this url will receive the data return by this method.__(/topic is used to broadcast its in config)
    public Message getContent(@Payload Message message, SimpMessageHeaderAccessor accessor) {
        accessor.getSessionAttributes().put("user","set user from payload");
        //to set user to the socket connection

    }*/
