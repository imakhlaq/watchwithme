package in.watch.auth.service;

import in.watch.auth.dto.AddUserDTO;
import in.watch.auth.dto.AuthResponse;
import in.watch.auth.dto.AuthUserDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Override
    public AuthResponse register(AddUserDTO userDTO) {
        return null;
    }

    @Override
    public AuthResponse authenticate(AuthUserDTO userDTO) {
        return null;
    }
}
