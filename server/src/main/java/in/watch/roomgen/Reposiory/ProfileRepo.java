package in.watch.roomgen.Reposiory;

import in.watch.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileRepo extends JpaRepository<UserProfile, UUID> {
}
