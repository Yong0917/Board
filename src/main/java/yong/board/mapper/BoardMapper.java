package yong.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import yong.board.vo.BoardVO;
import yong.board.vo.FileVO;

import java.util.List;

@Repository
@Mapper
public interface BoardMapper {
    //게시글 개수
    public int boardCount() throws Exception;

    //게시글 목록
    public List<BoardVO> boardList() throws Exception;

    //게시글 상세
    BoardVO boardDetail(int bno) throws Exception;

    //게시글 작성
    void boardInsert(BoardVO boardVO) throws Exception;

    //게시글 수정
    void boardUpdate(BoardVO board) throws Exception;

    //게시글 삭제
    void boardDelete(int bno) throws Exception;

    //파일 삭제
    void fileDelete(int bno) throws Exception;

    //댓글 삭제
    void commentDelete(int bno) throws Exception;

    //게시판 리스트
    List<BoardVO> selectBoardList(BoardVO param);

    //파일 경로 삽입
    public void FileInsert(FileVO fileVO) throws Exception;

    //파일 경로 다운
    public FileVO fileDetail(int bno) throws Exception;

    public int updateReviewCnt(int bno);

}
