package yong.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import yong.board.service.RegisterService;
import yong.board.vo.MemberVo;

public class MyAuthenticationProvider implements AuthenticationProvider {




    @Autowired
    RegisterService myUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String loginId = (String) authentication.getPrincipal();
        String loginPass = (String) authentication.getCredentials();

        System.out.println(loginId);
        System.out.println(loginPass);
        System.out.println(authentication);

        MemberVo mud = (MemberVo) myUserDetailsService.loadUserByUsername(loginId);

        if(mud == null || !mud.getPassword().equals(loginPass)) return null;

        //(principal, credentials, authorities)
        return new UsernamePasswordAuthenticationToken(loginId, loginPass, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
