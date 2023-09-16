package in.watch.socket;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocketController {

    @GetMapping("/value")
    public ResponseEntity<String> getData() {
        return ResponseEntity.ok("Hello from secure");
    }

}
