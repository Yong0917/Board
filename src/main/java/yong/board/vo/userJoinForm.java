package yong.board.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class userJoinForm {

    @NotBlank(message = "id를 입력해 주세요")
    private String id;

    @NotBlank
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 16자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해 주세요")
    private String username;

    @NotBlank(message = "올바른 나이를 입력해 주세요")
    @Range(min = 1, max = 110)
    private String age;

    @NotBlank
    @Email(message = "형식에 맞는 이메일을 입력해 주세요")
    private String email;

}
