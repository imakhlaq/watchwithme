package in.watch.auth.repo;

import in.watch.auth.model.User;
import in.watch.model.UserProfile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findAllByEmailIs(String email);

    //related to roomgen
    @Modifying
    @Query(value = "UPDATE User u SET u.profile = :profileId WHERE u.id = :userId")
    void updateProfile(@Param("profileId") UserProfile profileId, @Param("userId") UUID userId);
}
