package in.watch.auth.service;

import in.watch.auth.config.JwtService;
import in.watch.auth.dto.AddUserDTO;
import in.watch.auth.dto.AuthResponse;
import in.watch.auth.dto.AuthUserDTO;
import in.watch.auth.model.Role;
import in.watch.auth.model.User;
import in.watch.auth.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo repo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public AuthResponse register(AddUserDTO userDTO) {
        var user = User.builder().email(userDTO.getEmail())
                .name(userDTO.getName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER).build();

        repo.save(user);

        // user ==>UserDetails
        String token = jwtService.generateToken(new HashMap<>(), user);

        return AuthResponse.builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .token(token).build();

    }

    @Override
    public AuthResponse authenticate(AuthUserDTO userDTO) {
        System.out.println(userDTO);
        //Authentication is done by the AuthenticationManager Bean
        //And if they are not correct an exception will be thrown
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
        );

        //here means username and pass is correct
        var user = repo.findAllByEmailIs(userDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));//throw and handle exception
        System.out.println(user);

        // user ==>UserDetails
        String token = jwtService.generateToken(new HashMap<>(), user);

        return AuthResponse.builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .token(token).build();
    }
}
