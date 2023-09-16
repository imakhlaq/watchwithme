package in.watch.auth.service;

import in.watch.auth.dto.AddUserDTO;
import in.watch.auth.dto.AuthResponse;
import in.watch.auth.dto.AuthUserDTO;

public interface IAuthService {
    AuthResponse register(AddUserDTO userDTO);

    AuthResponse authenticate(AuthUserDTO userDTO);
}
