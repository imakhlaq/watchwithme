package in.watch.rooms.service;

import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class RoomService {

    public InputStreamSource streamVideo() throws IOException {

        var fileStream = new FileInputStream("F:\\earth.mp4");
        try (fileStream) {
            return new InputStreamSource() {
                @Override
                public InputStream getInputStream() throws IOException {
                    return fileStream;
                }
            };

        } catch (IOException e) {
            throw new IOException("File nor found");
        }
    }


}
