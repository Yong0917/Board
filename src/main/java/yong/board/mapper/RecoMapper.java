package yong.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import yong.board.vo.RecoVO;

import java.util.List;

@Repository
@Mapper
public interface RecoMapper {

    List<RecoVO> checkRecommend(RecoVO recoVO);

    void insertRecommend(RecoVO recoVO);

    int recommendCnt(int bno);

    void deleteRecommend(RecoVO recoVO);
}
