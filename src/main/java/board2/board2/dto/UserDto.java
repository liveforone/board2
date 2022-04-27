package board2.board2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {  //dto는 수정이 불가능한 객체

    private String name;
    private String email;
    private String password;
    private String auth;
    private String picture;
}