package yong.board.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yong.board.service.CommentService;
import yong.board.vo.CommentVO;

import java.util.List;

@Controller
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @ResponseBody
    @GetMapping("/comment/list") //댓글 리스트
    private List<CommentVO> CommentServiceList(int bno) throws Exception{

        return commentService.commentListService(bno);
    }

    @ResponseBody
    @PostMapping("/comment/insert") //댓글 작성
    private int CommentServiceInsert(@RequestParam int bno, @RequestParam String content, @RequestParam String writer) throws Exception{
        CommentVO comment = new CommentVO();
        comment.setBno(bno);
        comment.setContent(content);
        comment.setWriter(writer);

        return commentService.commentInsertService(comment);
    }

    @RequestMapping("/comment/update") //댓글 수정
    @ResponseBody
    private int CommentServiceUpdateProc(@RequestParam int cno, @RequestParam String content) throws Exception{

        CommentVO comment = new CommentVO();
        comment.setCno(cno);
        comment.setContent(content);

        return commentService.commentUpdateService(comment);
    }

    @ResponseBody
    @PostMapping("/comment/delete/{cno}") //댓글 삭제
    private int CommentServiceDelete(@PathVariable int cno) throws Exception{

        return commentService.commentDeleteService(cno);
    }

    @ResponseBody
    @GetMapping("/commentCnt") //댓글 갯수
    private int commentCnt(int bno) throws Exception{

        return commentService.commentCnt(bno);
    }

}

