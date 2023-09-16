package in.watch.roomgen.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomGenRes {
    private HttpStatusCode statusCode;
    private String roomId;
    private LocalDateTime timestamp;

}
