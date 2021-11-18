package yong.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yong.board.user.SessionUser;
import yong.board.vo.MemberVo;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/main")    //세션값 없으면 redirect
    public String main(Model model, HttpSession session) {

        return "main";
    }

    @GetMapping("/") //root 로그인화면
    public String login(Model model,HttpSession session) {

        SessionUser user = (SessionUser) session.getAttribute("user");

        //SSO 로그인후 session 저장
        if(user != null){
            session.setAttribute("id",user.getName());
            session.setAttribute("auth","User");
            session.setAttribute("name",user.getEmail());
            session.setAttribute("loginCheck", true);
            return "main";
        }
        else
            return "Login"; //session 없으면 로그인화면
    }

    @RequestMapping(value="/logout")        //로그아웃 후 세션 제거
    public String logoutProcess(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }

}
