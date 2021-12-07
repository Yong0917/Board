package yong.board.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yong.board.mapper.BoardMapper;
import yong.board.vo.BoardVO;
import yong.board.vo.FileVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Service
public class BoardService {

    private BoardMapper boardmapper;

    @Autowired
    public BoardService(BoardMapper boardmapper) {
        this.boardmapper = boardmapper;

    }
    //게사판 리스트
    public List<BoardVO> boardListService() throws Exception{

        return boardmapper.boardList();
    }
    //게시판 상세
    public BoardVO boardDetailService(int bno) throws Exception{

        return boardmapper.boardDetail(bno);
    }
    //게시판 작성 삽입
    public void boardInsertService(BoardVO boardVO) throws Exception{
        boardmapper.boardInsert(boardVO);
    }



    public List<BoardVO> selectBoardList(BoardVO param ) {
        return boardmapper.selectBoardList(param);
    }

    //게시판 업데이트
    public void boardUpdateService(BoardVO boardVO) throws Exception{

        boardmapper.boardUpdate(boardVO);
    }
    //게시판삭제
    public void boardDeleteService(int bno) throws Exception{

        boardmapper.boardDelete(bno);
    }
    //파일삭제
    public void fileDeleteService(int bno) throws Exception{

        boardmapper.fileDelete(bno);
    }
    //댓글삭제
    public void commentDeleteService(int bno) throws Exception{

        boardmapper.commentDelete(bno);
    }

    //파일 업로드
    public void fileInsertService(FileVO fileVO) throws Exception{
        boardmapper.FileInsert(fileVO);
    }
    //파일상세
    public FileVO fileDetailService(int bno) throws Exception{

        return boardmapper.fileDetail(bno);
    }

    //클릭 갯수
    public int updateReviewCnt(int bno) throws Exception {
        return boardmapper.updateReviewCnt(bno);
    }

    public List<BoardVO> selectRecList(BoardVO param) {
        return boardmapper.selectRscList(param);
    }

    //파일다운로드로직
    public void fileDownload(FileVO fileVO, HttpServletRequest request, HttpServletResponse response) {

        try{
            String fileUrl = fileVO.getFileurl();
            fileUrl += "/";
            String savePath = fileUrl;
            String fileName = fileVO.getFilename();

            //실제 내보낼 파일명
            String oriFileName = fileVO.getFileOriName();
            InputStream in = null;
            OutputStream os = null;
            File file = null;
            boolean skip = false;
            String client = "";

            //파일을 읽어 스트림에 담기
            try{
                file = new File(savePath, fileName);
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }

            client = request.getHeader("User-Agent");

            //파일 다운로드 헤더 지정
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "JSP Generated Data");

            if (!skip) {
                // IE
                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                    // IE 11 이상.
                } else if (client.indexOf("Trident") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    // 한글 파일명 처리
                    response.setHeader("Content-Disposition",
                            "attachment; filename=\"" + new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length", "" + file.length());
                os = response.getOutputStream();
                byte b[] = new byte[(int) file.length()];
                int leng = 0;
                while ((leng = in.read(b)) > 0) {
                    os.write(b, 0, leng);
                }
            } else {
                response.setContentType("text/html;charset=UTF-8");
            }
            in.close();
            os.close();
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }
}
