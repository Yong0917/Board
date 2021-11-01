package yong.board.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@Data
public class MemberVo implements UserDetails {

    private String id;
    private String password;
    private String username;
    private String age;
    private String auth;
    private String regDate;
    private String secretKey;
    private String qrCord;
    private String mfaCode;
    private String currentPwd;
    private String newPwd;
    private String writer;
    private String subject;
    private String content;
    private String email;
    private int bno;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


}
