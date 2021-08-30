package yong.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yong.board.service.RecoService;
import yong.board.vo.MemberVo;
import yong.board.vo.RecoVO;

import java.util.List;


@Controller
public class RecoController {

    private RecoService recoService;

    @Autowired
    public RecoController(RecoService recoService) {
        this.recoService = recoService;
    }

    @ResponseBody
    @RequestMapping(value = "/clickRecommend")
    public String clickRecommend(RecoVO recoVO) throws Exception{

        List<RecoVO> list =recoService.checkRecommend(recoVO);  //이미 게시글 추천했는지 체크

        if(list.size() == 0){   // 비어있으면 추천아직 안한거

            recoService.insertRecommend(recoVO);    // 추천 insert

            return "Insert";

        }
        else{

            recoService.deleteRecommend(recoVO);

            return "Delete";

        }


    }

    @RequestMapping("/recommendCnt") //댓글 갯수
    @ResponseBody
    private int recommendCnt(int bno) throws Exception{

        return recoService.recommendCnt(bno);
    }

}
