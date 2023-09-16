package in.watch.auth.model;

import in.watch.model.UserProfile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
//UserDetails is for spring security
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @Column(unique = true)
    private String email;

    private String password;
    //telling that you want to use enum with string value
    @Enumerated(EnumType.STRING)
    private Role role;


    @ToString.Exclude
    //related to roomgen
    @OneToOne(cascade = CascadeType.ALL)
    //"profile_id"=>it will be colum name, and it will reference profile id
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private UserProfile profile;


    //this should return a list of roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    //this should return username or email (unique thing for user) and it will be referred as username in spring security
    @Override
    public String getUsername() {
        return this.email;
    }

    //you have to also override get password
    // (if the password field is called password, then lombok can provide implementation for it)
    @Override
    public String getPassword() {
        return this.password;
    }

    //these if you have expiry or locked account functionality, add logic for that
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


