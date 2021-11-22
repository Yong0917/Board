package yong.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import yong.board.user.OAuthAttributes;
import yong.board.user.SessionUser;
import yong.board.user.User;
import yong.board.vo.RegisterVO;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    private final RegisterService registerService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 서비스 id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user)); // SessionUser을 이용해 session 저장

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("USER")), //ROLE 기본값 USER로 설정
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 유저 생성 및 수정 서비스 로직
    private User saveOrUpdate(OAuthAttributes attributes) {

        User user = new User(attributes.getEmail(),attributes.getName(),attributes.getPicture());

        RegisterVO registerVO = new RegisterVO();
        registerVO.setId(attributes.getEmail());
        registerVO.setUsername(attributes.getName());
        registerVO.setAuth("User");

        //SSO전용 DB등록
        List<RegisterVO> list =registerService.checkSSO(registerVO);

        if(list.size() == 0) {
            registerService.joinUser(registerVO);       //SSO정보 등록
            return user;
        }
        else        //이미 존재할시 return
            return user;

    }
}
