package yong.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yong.board.vo.MemberVo;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/main")
    public String main(Model model, HttpSession session) {

        String id = (String) session.getAttribute("id");
        MemberVo param =new MemberVo();
        param.setId(id);

//        if(session.getAttribute("rscgrp")!=null || session.getAttribute("rscgrp")!="" ) model.addAttribute("grpList", homeService.getGrpList());


        if(session.getAttribute("id")==null) return "redirect:/";
        else return "main";

    }

    @RequestMapping(value="/logout")
    public String logoutProcess(HttpSession session) {

        session.removeAttribute("loginCheck");
        session.removeAttribute("id");
        session.removeAttribute("auth");
        session.removeAttribute("name");
        session.removeAttribute("age");


        return "redirect:/";
    }

    @GetMapping("/memberInfo")
    public String memberInfo(Model model, HttpSession session) {

        if(session.getAttribute("id")==null) return "redirect:/";
        else return "main";

    }
}
