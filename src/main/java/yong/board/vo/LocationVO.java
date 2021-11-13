package yong.board.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationVO {
    public int total;
    public String category;
    public String title;
    public String link;
    public String description;
    public String address;
    public String roadAddress;
    public String mapx;
    public String mapy;

}
