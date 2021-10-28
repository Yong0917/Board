package yong.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import yong.board.vo.RecoVO;

import java.util.List;

@Repository
@Mapper
public interface RecoMapper {

    //추천리스트출력
    List<RecoVO> checkRecommend(RecoVO recoVO);

    void insertRecommend(RecoVO recoVO);

    //추천 갯수
    int recommendCnt(int bno);

    //추천 삭제
    void deleteRecommend(RecoVO recoVO);
}
