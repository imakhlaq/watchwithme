package in.watch.model;

import in.watch.auth.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_profile")
@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String roomId;
    private Integer roomMemberCount;

    //"profile" because owning side have field profile
    @OneToOne(mappedBy = "profile")
    private User user;

}
