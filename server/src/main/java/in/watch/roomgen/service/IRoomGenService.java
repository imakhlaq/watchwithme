package in.watch.roomgen.service;

import in.watch.auth.model.User;
import in.watch.roomgen.dao.RoomGenRes;
import org.springframework.http.ResponseEntity;

public interface IRoomGenService {

    RoomGenRes createNewRoom(User user);

}
