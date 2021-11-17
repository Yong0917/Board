package yong.board.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginVO {

    @NotEmpty
    private String id;

    @NotEmpty
    private String password;
}
