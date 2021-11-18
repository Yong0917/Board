package yong.board.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginVO {

    @NotEmpty(message = "id를 입력해주세요.")
    private String id;

    @NotEmpty@NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
}
