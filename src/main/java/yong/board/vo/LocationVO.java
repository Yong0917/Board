package yong.board.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationVO {
    private int total;
    private String category;
    private String title;
    private String link;
    private String description;
    private String address;
    private String roadAddress;
    private String mapx;
    private String mapy;

}
