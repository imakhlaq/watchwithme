package in.watch.roomgen;

import in.watch.auth.model.User;
import in.watch.roomgen.dao.RoomGenRes;
import in.watch.roomgen.service.IRoomGenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RoomGenController {

    @Autowired
    IRoomGenService roomGenService;

    @GetMapping("/createRoom")
    public ResponseEntity<RoomGenRes> generateRoomId(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(this.roomGenService.createNewRoom(user));
    }
}
