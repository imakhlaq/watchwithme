package in.watch.auth.controller;

import in.watch.auth.dto.AddUserDTO;
import in.watch.auth.dto.AuthResponse;
import in.watch.auth.dto.AuthUserDTO;
import in.watch.auth.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AddUserDTO userDTO) {
        return ResponseEntity.ok(this.authService.register(userDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthUserDTO userDTO) {
        return ResponseEntity.ok(this.authService.authenticate(userDTO));
    }
}
