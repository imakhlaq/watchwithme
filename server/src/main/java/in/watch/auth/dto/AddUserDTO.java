package in.watch.auth.dto;


import lombok.Data;

@Data
public class AddUserDTO {
    private String name;
    private String email;
    private String password;
}
