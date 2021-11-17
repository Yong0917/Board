package yong.board.controller;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yong.board.TOTPTokenValidation;
import yong.board.service.RegisterService;
import yong.board.vo.LoginVO;
import yong.board.vo.MemberVo;
import yong.board.vo.RegisterVO;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.List;
import java.util.Properties;
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
    public String joinMember(@Validated RegisterVO registerVO, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {        //valid error
            return "Error";
        }

        List<RegisterVO> list =registerService.checkMember(registerVO);

        String retVal = null;

        // 신규 고객일 경우
        if(list.size() == 0) {

            String secretKey = generateSecretKey();
            registerVO.setSecretKey(secretKey);

            String qrId = "Board Community"+"("+registerVO.getId()+")"; //QRcord 화면에 표시할 id
            String barcodeUrl = getGoogleAuthenticatorBarCode(secretKey, qrId);     //sercret key와 qrid 넘겨줌
            registerVO.setQrCord(barcodeUrl);
            registerVO.setAuth("User");

            registerService.joinUser(registerVO);
            retVal = "Success";
        }
        // 기존에 요청했던 고객일 경우
        else{

            retVal = "fail";
        }

        return retVal;
    }

    @PostMapping("/login.do")
    public String Login(LoginVO loginVO, HttpServletRequest request, Model model) {

//        if (bindingResult.hasErrors()) {        //valid error
//            return "Login";
//        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        MemberVo user = (MemberVo) registerService.loadUserByUsername(loginVO.getId()); // user 정보 모두

        if (user == null) { // user값 체크
            return "Login";

        } else {
            // 비밀번호 비교
            if (!passwordEncoder.matches(loginVO.getPassword(), user.getPassword())) {

                return "Login";

            } else {
                //로그인 성공 처리
                //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
                HttpSession session = request.getSession();
                session.setAttribute("loginCheck", "SUCCESS");

                model.addAttribute("loginMember",user);

                return "mfaLogin";

            }

        }
    }

    @GetMapping(value = "/mfaLogin")
    public String mfaLogin(HttpSession session, Model model) {
        String loginCheck = (String) session.getAttribute("loginCHeck");

        // login 통과못하면 Login 화면으로
        if(loginCheck == null || "".equals(loginCheck)){
           return "redirect:/";
        }
        return "mfaLogin";
    }

    //구글 유효값 인증
    @PostMapping("/googleVerify.do")
    public String equalCode(MemberVo memberVo, HttpSession session,Model model) {

        String userSecretKey = registerService.selectSecretKey(memberVo);

        String inputCode =  memberVo.getMfaCode();
        //입력한 값과 secretkey 비교
        if (TOTPTokenValidation.validate(inputCode,userSecretKey)) {
            //     if (TOTPTokenValidation.validate(inputCode,userSecretKey)) {
            MemberVo user = registerService.selectMember(memberVo.getId());

            //로그인 성공 시 세션값 설정
            session.setAttribute("loginCheck",true);
            session.setAttribute("id",user.getId());
            session.setAttribute("auth",user.getAuth());
            session.setAttribute("name",user.getUsername());
            session.setAttribute("age",user.getAge());
            session.setAttribute("loginUser",user);
            return "main";
        }
        else {
            model.addAttribute("loginMember",memberVo);
            return "mfaLogin";
        }
    }


    //회원가입 email Code 전송
    @ResponseBody
    @RequestMapping(value = "/emailCode", method = RequestMethod.GET)
    public String emailCode(MemberVo memberVo) throws MessagingException {
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
