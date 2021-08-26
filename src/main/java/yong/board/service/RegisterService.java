package yong.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yong.board.mapper.RegisterMapper;
import yong.board.vo.MemberVo;

import java.util.List;

@Service
public class RegisterService implements UserDetailsService {

    public final RegisterMapper registerMapper;

    @Autowired
    public RegisterService(RegisterMapper registerMapper) {
        this.registerMapper = registerMapper;
    }

    public List<MemberVo> checkMember(MemberVo memberVo) {
        return registerMapper.checkMember(memberVo);
    }

    public void joinUser(MemberVo memberVo) {
        //패스워드 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberVo.setPassword(passwordEncoder.encode(memberVo.getPassword()));
        registerMapper.joinMember(memberVo);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberVo user =  registerMapper.selectMember(username);

        if(user==null) {
            throw new UsernameNotFoundException(username);
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
}
