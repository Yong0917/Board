package yong.board.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yong.board.mapper.CommentMapper;
import yong.board.vo.CommentVO;

import java.util.List;

@Service
public class CommentService {

    private CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;

    }

    public List<CommentVO> commentListService(int bno) throws Exception{

        return commentMapper.commentList(bno);
    }

    public int commentInsertService(CommentVO comment) throws Exception{

        return commentMapper.commentInsert(comment);
    }

    public int commentUpdateService(CommentVO comment) throws Exception{

        return commentMapper.commentUpdate(comment);
    }

    public int commentDeleteService(int cno) throws Exception{

        return commentMapper.commentDelete(cno);
    }

    public int commentCnt(int bno) {
        if(commentMapper.commentCnt(bno) == 0)
            return 0;
        else
            return commentMapper.commentCnt(bno);
    }
}


