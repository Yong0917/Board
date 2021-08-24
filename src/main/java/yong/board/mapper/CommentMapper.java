package yong.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import yong.board.vo.CommentVO;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {

    public int commentCount() throws Exception;


    public List<CommentVO> commentList(int bno) throws Exception;


    public int commentInsert(CommentVO commentVO) throws Exception;


    public int commentUpdate(CommentVO commentVO) throws Exception;


    public int commentDelete(int cno) throws Exception;

}
