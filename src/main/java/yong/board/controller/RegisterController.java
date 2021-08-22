package yong.board.controller;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yong.board.TOTPTokenValidation;
import yong.board.service.RegisterService;
import yong.board.vo.MemberVo;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.List;

@Controller
public class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/")
    public String login(Model model) {

        return "Login";
    }

    @GetMapping("/userjoin")
    public String newUser(Model model) {

        return "userJoin";
    }

    @ResponseBody
    @RequestMapping(value = "/registerUser")
    public String joinMember(MemberVo memberVo){

        List<MemberVo> list =registerService.checkMember(memberVo);

        String retVal = null;

        // 신규 고객일 경우
        if(list.size() == 0) {

            String secretKey = generateSecretKey();
            memberVo.setSecretKey(secretKey);

            String qrId = memberVo.getId();
            String barcodeUrl = getGoogleAuthenticatorBarCode(secretKey, qrId);
            memberVo.setQrCord(barcodeUrl);
            memberVo.setAuth("User");

            /*try {
                sendregistermail(memberVo.getEmail(),firstPasswd);
                //sendtmmail(memberVo.getEmail());

                // sendMailRepeatRequest(memberVo.getEmail());
            } catch (MessagingException e) {
                logger.error(CommonUtil.getPrintStackTrace(e));
            }*/
            registerService.joinUser(memberVo);
            retVal = "Success";
        }
        // 기존에 요청했던 고객일 경우
        else{
            // 계정이 이미 있으므로 신청 내역이 있다는 내용 메일 전송해 주기
           /* try {
                sendMailRepeatRequest(memberVo.getEmail());
            } catch (MessagingException e) {
                logger.error(CommonUtil.getPrintStackTrace(e));
            }*/
            retVal = "fail";
        }

        // 고객 유입 메일 전송
        /*try {
            sendMailCustomerInflux(memberVo.getEmail());
        } catch (MessagingException e) {
            logger.error(CommonUtil.getPrintStackTrace(e));
        }*/

        return retVal;
    }

    @ResponseBody
    @RequestMapping(value = "/login.do")
    public String Login(MemberVo memberVo, HttpSession session) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        MemberVo user = (MemberVo) registerService.loadUserByUsername(memberVo.getId());

        if (user == null) {
            return "idNull";

        } else {

            if (!passwordEncoder.matches(memberVo.getPassword(), user.getPassword())) {

                return "pwCheck";

            } else {


                session.setAttribute("loginCheck", true);
 /*                session.setAttribute("email",memberVo.getEmail());
                session.setAttribute("auth",user.getAuth());
                session.setAttribute("rscgrp",user.getRscGrp());
                session.setAttribute("step",user.getStep());*/

                return "Success";
            }

        }
    }

    @ResponseBody
    @RequestMapping(value = "/getImage.do")
    public String googleUrl(MemberVo memberVo) {
        String userQrCord = registerService.selectQrCord(memberVo);
        return userQrCord;
    }

    @ResponseBody
    @RequestMapping(value = "/googleVerify.do")
    public String equalCode(MemberVo memberVo, HttpSession session) {

        String userSecretKey = registerService.selectSecretKey(memberVo);
        String inputCode =  memberVo.getMfaCode();
        //TOTPTokenValidation.
        //업로드시주석해제

        if (TOTPTokenValidation.validate(inputCode,userSecretKey)) {
            //     if (TOTPTokenValidation.validate(inputCode,userSecretKey)) {
            MemberVo user = registerService.selectMember(memberVo.getId());
            System.out.println(user.getAuth());
            session.setAttribute("loginCheck",true);
            session.setAttribute("email",memberVo.getId());
            session.setAttribute("auth",user.getAuth());
            session.setAttribute("rscgrp",user.getUsername());
            session.setAttribute("step",user.getAge());
            return "success";
        }
        else {
            return "fail";
        }
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

        try {//igloosec          //igloosec
            return GOOGLE_URL+"otpauth://totp/"
                    + URLEncoder.encode(issuer , "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }


}
