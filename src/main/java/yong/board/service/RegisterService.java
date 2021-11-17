package yong.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yong.board.mapper.RegisterMapper;
import yong.board.vo.MemberVo;
import yong.board.vo.RegisterVO;

import java.util.List;

@Service
public class RegisterService implements UserDetailsService {

    public final RegisterMapper registerMapper;

    @Autowired
    public RegisterService(RegisterMapper registerMapper) {
        this.registerMapper = registerMapper;
    }

    public List<RegisterVO> checkMember(RegisterVO registerVO) {
        return registerMapper.checkMember(registerVO);
    }

    public void joinUser(RegisterVO registerVO) {
        //패스워드 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(registerVO.getPassword() == null)      //SSO는 패스워드 필요X
            registerMapper.registerSSO(registerVO);       //SSO전용
        else{
            registerVO.setPassword(passwordEncoder.encode(registerVO.getPassword()));
            registerMapper.joinMember(registerVO);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberVo user =  registerMapper.selectMember(username);

        if(user==null) {
            //throw new UsernameNotFoundException(username);
        }
        return user;

    }

    public String selectQrCord(MemberVo memberVo) {

        return registerMapper.selectQrCord(memberVo);
    }

    public String selectSecretKey(MemberVo memberVo) {
        return registerMapper.selectSecretKey(memberVo);
    }

    public MemberVo selectMember(String id) {
        return registerMapper.selectMember(id);
    }

    //SSO 전적 확인
    public List<RegisterVO> checkSSO(RegisterVO member) {
        return registerMapper.checkSSO(member);
    }
}
