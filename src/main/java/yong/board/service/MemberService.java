package yong.board.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yong.board.mapper.MemberMapper;
import yong.board.vo.MemberVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
        mapper.recommendDelete(memberVo);     // 작성했던 추천 삭제

    }

    public List<MemberVo> selectCommentList(String id) {
        return mapper.selectCommentList(id);
    }

    public String pwSchedule(String id) {

        String pwChange = "";

        try {

            String userPwDate = mapper.pwSchedule(id);  //user pw 업데이트 시간

            String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); //현재시간

            SimpleDateFormat fDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.KOREA);

            Date userDate = fDate.parse(userPwDate);
            Date currentDate = fDate.parse(formatDate);

            long userDate2 = userDate.getTime();
            long currentDate2 = currentDate.getTime();

            long userPwHours = TimeUnit.MILLISECONDS.toHours(userDate2);        //마지막 패스워드 수정날짜를 시간단위로
            long currentHours = TimeUnit.MILLISECONDS.toHours(currentDate2);

            if((currentHours - userPwHours) >= 720) pwChange = "Change";        // 30일 -> 720hours
            else pwChange = "noChange";

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return pwChange;

    }
}
