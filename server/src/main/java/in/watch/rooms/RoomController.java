package in.watch.rooms;


import in.watch.rooms.service.RoomService;
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

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

@Controller
@Slf4j
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    public void getContent() throws IOException {

        //getting file from Disk
        Path videoPath = Paths.get("F:\\testFile.mp4");

        //open stream from the file
        InputStream videoInputStream = Files.newInputStream(videoPath, StandardOpenOption.READ);

  /*      // Create an InputStreamSource from the InputStream
        InputStreamSource source = new InputStreamResource(videoInputStream);*/


        // InputStream is = source.getInputStream();

        //buffer array
        byte[] buffer = new byte[1024];
        int bytesRead;

        //sending video in buffer to FE
        while ((bytesRead = videoInputStream.read(buffer)) != -1) {

            var base64 = Base64.getEncoder().encodeToString(buffer);

            //test Code
            PrintWriter writer = new PrintWriter(new FileOutputStream("F:\\testFile.txt", true));
            writer.print(base64);
            writer.flush();
            writer.close();

            messagingTemplate.convertAndSend("/topic/data", base64);
        }
        videoInputStream.close();
        // is.close();
    }
}




/*
   if you want to add data in socket session
    @MessageMapping("/adduser")  //client will send message on this =>/app/message
    @SendTo("/topic/adduser")
    //any client subscribed to this url will receive the data return by this method.__(/topic is used to broadcast its in config)
    public Message getContent(@Payload Message message, SimpMessageHeaderAccessor accessor) {
        accessor.getSessionAttributes().put("user","set user from payload");
        //to set user to the socket connection

    }
*/
