package yong.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yong.board.mapper.RegisterMapper;
import yong.board.vo.MemberVo;
import yong.board.vo.RegisterVO;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.Random;

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


    //이메일 인즌 메일 전송
    public String sendMail(MemberVo memberVo) {

        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;     //랜덤코드 6자리 생성

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setProtocol("smtp");
        mailSender.setUsername("seungyong917@gmail.com");
        mailSender.setPassword("testtesttest");

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.debug", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.EnableSSL.enable", "true");

        mailSender.setJavaMailProperties(prop);

        String toMail = memberVo.getEmail();
        String title = "통합게시판 커뮤니티 회원가입 인증 이메일 입니다.";
        String content =
                "통합게시판 커뮤니티를 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + checkNum + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
        try{
            MimeMessage mimeMsg = mailSender.createMimeMessage();
            mimeMsg.setFrom(new InternetAddress("BoardAdmin@board.com"));
            mimeMsg.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));//수취인
            mimeMsg.setSubject(title, "utf-8");
            mimeMsg.setContent(content, "text/html; charset=utf-8");

            mailSender.send(mimeMsg);

        }catch(Exception e){
            e.printStackTrace();
        }
        return Integer.toString(checkNum);
    }
}
