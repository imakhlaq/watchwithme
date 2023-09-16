package in.watch.auth.dto;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
public class AuthResponse {
    private HttpStatusCode statusCode;
    private String message;
    private String token;
    private LocalDateTime timestamp;
}
