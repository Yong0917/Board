package yong.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yong.board.service.MemberService;
import yong.board.vo.MemberVo;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member")  //세션값 없으면 redirect
    public String memberInfo(Model model, HttpSession session) {

        if(session.getAttribute("id")==null) return "redirect:/";
        else return "member";

    }

    @ResponseBody   //사용자 리스트
    @RequestMapping(value = "/getMemberList.do")
    public List<MemberVo> getMemberList(Model model) {

        List<MemberVo>  list = memberService.selectMemberList();
        model.addAttribute("list",list);

        return list;
    }

    @ResponseBody   //사용자 정보
    @RequestMapping(value = "/getUserInfo")
    public MemberVo getUserInfo(MemberVo memberVo) {
        return memberService.getUserInfo(memberVo.getId());
    }


    @ResponseBody   //비밀번호 수정
    @RequestMapping(value = "/modifyUserPwd")
    public String modifyUserPwd(MemberVo memberVo) {
        return memberService.modifyUserPwd(memberVo);
    }


    @PostMapping("/deleteUser") //사용자 삭제
    public String deleteMember(MemberVo memberVo) {

        memberService.deleteUser(memberVo);

        return "redirect:/member";
    }

}
