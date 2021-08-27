package yong.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import yong.board.vo.CommentVO;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {

    //comment 개수
    public int commentCount() throws Exception;

    //comment 리스트
    public List<CommentVO> commentList(int bno) throws Exception;

    //comment 삽입
    public int commentInsert(CommentVO commentVO) throws Exception;

    //comment 수정
    public int commentUpdate(CommentVO commentVO) throws Exception;

    //comment 삭제
    public int commentDelete(int cno) throws Exception;

    int commentCnt(int bno);
}
