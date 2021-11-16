package yong.board.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieVO {
    public String title;
    public String link;
    public String image;
    public String subtitle;
    public String pubDate;
    public String director;
    public String actor;
    public String userRating;

}
