package yong.board.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileVO {
    private int fno;
    private int bno;
    private String filename;     //저장할 파일
    private String fileOriName;  //실제 파일
    private String fileurl;


}
