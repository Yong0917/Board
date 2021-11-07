package yong.board.controller;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yong.board.TOTPTokenValidation;
import yong.board.service.RegisterService;
import yong.board.vo.MemberVo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Controller
public class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/userjoin")    //회원가입
    public String newUser(Model model) {

        return "userJoin";
    }

    @ResponseBody   //회원가입 로직
    @PostMapping(value = "/registerUser")
    public String joinMember(MemberVo memberVo){

        List<MemberVo> list =registerService.checkMember(memberVo);

        String retVal = null;

        // 신규 고객일 경우
        if(list.size() == 0) {

            String secretKey = generateSecretKey();
            memberVo.setSecretKey(secretKey);

            String qrId = "Board Community"+"("+memberVo.getId()+")"; //QRcord 화면에 표시할 id
            String barcodeUrl = getGoogleAuthenticatorBarCode(secretKey, qrId);     //sercret key와 qrid 넘겨줌
            memberVo.setQrCord(barcodeUrl);
            memberVo.setAuth("User");

            registerService.joinUser(memberVo);
            retVal = "Success";
        }
        // 기존에 요청했던 고객일 경우
        else{

            retVal = "fail";
        }

        return retVal;
    }

    @ResponseBody
    @GetMapping(value = "/login.do")
    public String Login(MemberVo memberVo, HttpSession session) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        MemberVo user = (MemberVo) registerService.loadUserByUsername(memberVo.getId());

        if (user == null) { // user값 체크
            return "idNull";

        } else {
            // 비밀번호 비교
            if (!passwordEncoder.matches(memberVo.getPassword(), user.getPassword())) {

                return "pwCheck";

            } else {

                //검증 성공
                session.setAttribute("loginCheck", true);
                return "Success";
            }

        }
    }

    @ResponseBody   //qrcord 불러오기
    @GetMapping(value = "/getImage.do")
    public String googleUrl(MemberVo memberVo) {
        String userQrCord = registerService.selectQrCord(memberVo);
        return userQrCord;
    }

    @ResponseBody       //구글 유효값 인증
    @GetMapping(value = "/googleVerify.do")
    public String equalCode(MemberVo memberVo, HttpSession session) {

        String userSecretKey = registerService.selectSecretKey(memberVo);
        String inputCode =  memberVo.getMfaCode();

        //입력한 값과 secretkey 비교
        if (TOTPTokenValidation.validate(inputCode,userSecretKey)) {
            //     if (TOTPTokenValidation.validate(inputCode,userSecretKey)) {
            MemberVo user = registerService.selectMember(memberVo.getId());

            //로그인 성공 시 세션값 설정
            session.setAttribute("loginCheck",true);
            session.setAttribute("id",memberVo.getId());
            session.setAttribute("auth",user.getAuth());
            session.setAttribute("name",user.getUsername());
            session.setAttribute("age",user.getAge());
            return "success";
        }
        else {
            return "fail";
        }
    }


    //email Code 전송
    @ResponseBody
    @RequestMapping(value = "/emailCode", method = RequestMethod.GET)
    public void emailCode(MemberVo memberVo){
        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;




        String toMail = memberVo.getEmail();
        String title = "회원가입 인증 이메일 입니다.";
        String content =
                "홈페이지를 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + checkNum + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toMail);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(content);

        mailSender.send(simpleMailMessage);

    }



    //시크릿 키 생성
    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    //google인증 바코드 생성
    public static String getGoogleAuthenticatorBarCode(String secretKey, String issuer) {

        String GOOGLE_URL = "https://chart.googleapis.com/chart?chs=100x100&chld=M|0&cht=qr&chl=";

        try {
            return GOOGLE_URL+"otpauth://totp/"
                    + URLEncoder.encode(issuer , "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }


}
