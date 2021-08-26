package yong.board.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yong.board.mapper.MemberMapper;
import yong.board.vo.MemberVo;

import java.util.List;

@Service
public class MemberService {

    public final MemberMapper mapper;

    public MemberService(MemberMapper mapper) {
        this.mapper = mapper;
    }

    public List<MemberVo> selectMemberList() {
        return mapper.selectMemberList();
    }

    public MemberVo getUserInfo(String id) {
        return mapper.selectMember(id);
    }

    public String modifyUserPwd(MemberVo memberVo) {

        String msg = null;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        try {
            // 현재 비밀번호와 체크
            MemberVo user =  mapper.selectMember(memberVo.getId());

            if (!passwordEncoder.matches(memberVo.getCurrentPwd(), user.getPassword())) {
                msg = "현재 비밀번호가 일치하지 않습니다.";
            } else {
                // 새로운 패스워드 어베이트
                // 패스워드 암호화
                memberVo.setPassword(passwordEncoder.encode(memberVo.getNewPwd()));
                mapper.updatePw(memberVo);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return msg;
    }

    public void deleteUser(MemberVo memberVo) {

        mapper.deleteMember(memberVo);      //user_list id삭제
        mapper.boardDelete(memberVo);       // 작성했던 게시글 삭제
        mapper.commentDelete(memberVo);     // 작성했던 comment 삭제

    }
}
