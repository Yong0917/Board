package yong.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yong.board.mapper.RecoMapper;
import yong.board.vo.RecoVO;

import java.util.List;

@Service
public class RecoService {

    private RecoMapper mapper;

    @Autowired
    public RecoService(RecoMapper mapper) {
        this.mapper = mapper;

    }

    public List<RecoVO> checkRecommend(RecoVO recoVO) {
        return mapper.checkRecommend(recoVO);
    }

    public int recommendCnt(int bno) {
        if(mapper.recommendCnt(bno) == 0)
            return 0;
        else
            return mapper.recommendCnt(bno);
    }

    public void insertRecommend(RecoVO recoVO) {
        mapper.insertRecommend(recoVO);
    }

    public void deleteRecommend(RecoVO recoVO) {
        mapper.deleteRecommend(recoVO);
    }
}
