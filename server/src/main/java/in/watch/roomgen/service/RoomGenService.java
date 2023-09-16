package in.watch.roomgen.service;

import in.watch.auth.model.User;
import in.watch.auth.repo.UserRepo;
import in.watch.model.UserProfile;
import in.watch.roomgen.Reposiory.ProfileRepo;
import in.watch.roomgen.dao.RoomGenRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class RoomGenService implements IRoomGenService {

    @Autowired
    ProfileRepo profileRepo;
    @Autowired
    UserRepo userRepo;

    @Override
    public RoomGenRes createNewRoom(User user) {
        UUID uuid = UUID.randomUUID();
        String roomId = uuid.toString();

        UserProfile profile = null;
        UserProfile dbProfile = null;

        if (user.getProfile() == null) {

            profile = UserProfile.builder()
                    .roomMemberCount(0)
                    .user(user)
                    .roomId(roomId).build();
            dbProfile = profileRepo.save(profile);
        } else {

            profile = UserProfile.builder()
                    .roomMemberCount(0)
                    .user(user).id(user.getProfile().getId())
                    .roomId(roomId).build();
            dbProfile = profileRepo.save(profile);
        }

        log.info(String.valueOf(user));
        log.info(String.valueOf(dbProfile));

        userRepo.updateProfile(dbProfile, user.getId());

        return RoomGenRes.builder()
                .roomId(roomId)
                .statusCode(HttpStatus.OK)
                .timestamp(LocalDateTime.now()).build();
    }
}
