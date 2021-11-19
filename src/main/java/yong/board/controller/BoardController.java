package yong.board.controller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yong.board.service.BoardService;
import yong.board.vo.BoardVO;
import yong.board.vo.FileVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
public class BoardController {

    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping("/insert") //게시글 작성폼 호출
    private String boardInsertForm(){

        return "insert";
    }

    @GetMapping("/movie")
    public String movie(Model model, HttpSession session){

        return "movie";
    }

    @GetMapping("/location")
    public String location(Model model, HttpSession session){

        return "location";
    }

    @GetMapping("/record")  //세션값 없으면 redirect
    public String record(Model model, HttpSession session) {

        return "record";

    }

    @RequestMapping("/detail") //게시글 작성폼 호출
    private String detail(BoardVO param , Model model) throws Exception{
        int bno = param.getBno();

        model.addAttribute("DETAIL", boardService.boardDetailService(bno));
        model.addAttribute("files", boardService.fileDetailService(bno));

        //조회수
        boardService.updateReviewCnt(bno);

        return "detail";
    }



    //게시글 작성
    @RequestMapping(value = "/insertProc")
    private String boardInsertProc(HttpServletRequest request,@RequestPart MultipartFile files) throws Exception{


        BoardVO board = new BoardVO();
        FileVO file  = new FileVO();

        board.setSubject(request.getParameter("subject"));
        board.setContent(request.getParameter("content"));
        board.setWriter(request.getParameter("writer"));


        if(files.isEmpty()){ //업로드할 파일이 없을 시
            boardService.boardInsertService(board); //게시글 insert
        }else{
            String fileName = files.getOriginalFilename();
            String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
            File destinationFile;
            String destinationFileName;

            String fileUrl ="";
            boolean isWindows = System.getProperty("os.name")
                    .toLowerCase().startsWith("windows");
            if (isWindows) {
                fileUrl = "D:\\uploadfile\\";
            }else {
                fileUrl = "test";
            }


            do {
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
                destinationFile = new File(fileUrl + destinationFileName);
//                new File(fileUrl+ destinationFileName);
            } while (destinationFile.exists());
            //RandomStringUtils
            destinationFile.getParentFile().mkdirs();
            files.transferTo(destinationFile);

            boardService.boardInsertService(board); //게시글 insert

            file.setBno(board.getBno());
            file.setFilename(destinationFileName);
            file.setFileOriName(fileName);
            file.setFileurl(fileUrl);

            boardService.fileInsertService(file); //file insert
        }


        return "redirect:/main";

    }


    //게시글 불러오기
    @ResponseBody
    @GetMapping(value = "/getBoardList.do")
    public List<BoardVO> getBoardList(Model model, BoardVO param)  {


        List<BoardVO> list = boardService.selectBoardList(param);
        model.addAttribute("list",list);

        return list;
    }


    //추천누른 게시글 가져오기
    @ResponseBody
    @GetMapping(value = "/getRecList.do")
    public List<BoardVO> getRecList(Model model, BoardVO param) {


        List<BoardVO> list = boardService.selectRecList(param);
        model.addAttribute("list", list);

        return list;

    }

    @RequestMapping("/update") //게시글 수정폼 호출
    private String boardUpdateForm(BoardVO param, Model model) throws Exception{
        int bno = param.getBno();

        model.addAttribute("DETAIL", boardService.boardDetailService(bno));

        return "update";
    }



    @RequestMapping("/updateProc")  //게시글 수정
    private String boardUpdateProc(HttpServletRequest request) throws Exception{

        BoardVO board = new BoardVO();
        board.setSubject(request.getParameter("subject"));
        board.setContent(request.getParameter("content1"));
        board.setBno(Integer.parseInt(request.getParameter("bno")));

        boardService.boardUpdateService(board);

        return "redirect:/detail?bno="+ request.getParameter("bno");
    }


    @RequestMapping("/delete")  //게시글 삭제
    private String boardDelete(BoardVO param, Model model) throws Exception{
        int bno = param.getBno();

        boardService.boardDeleteService(bno);       //게시글 삭제
        boardService.fileDeleteService(bno);        //파일 삭제
        boardService.commentDeleteService(bno);     //댓글 삭제

        return "redirect:/main";
    }

    @RequestMapping("/fileDown/{bno}") //파일 다운로드 로직
    private void fileDown(@PathVariable int bno, HttpServletRequest request, HttpServletResponse response) throws Exception{

        request.setCharacterEncoding("UTF-8");
        FileVO fileVO = boardService.fileDetailService(bno);

        boardService.fileDownload(fileVO,request,response); //파일 다운로드

    }




}
