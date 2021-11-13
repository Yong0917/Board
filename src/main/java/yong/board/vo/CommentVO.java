package yong.board.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentVO {

    private int cno;
    private int bno;
    private String content;
    private String writer;
    private String regDate;


}
