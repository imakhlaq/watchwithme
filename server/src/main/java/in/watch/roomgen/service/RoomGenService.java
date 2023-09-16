package in.watch.roomgen.service;

import in.watch.auth.model.User;
import in.watch.model.UserProfile;
import in.watch.roomgen.Reposiory.ProfileRepo;
import in.watch.roomgen.dao.RoomGenRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RoomGenService implements IRoomGenService {

    @Autowired
    ProfileRepo profileRepo;

    @Override
    public RoomGenRes createNewRoom(User user) {
        UUID uuid = UUID.randomUUID();
        String roomId = uuid.toString();

        var profile = UserProfile.builder()
                .roomMemberCount(0)
                .user(user)
                .roomId(roomId).build();

        profileRepo.save(profile);

        return RoomGenRes.builder()
                .roomId(roomId)
                .statusCode(HttpStatus.OK)
                .timestamp(LocalDateTime.now()).build();
    }
}
