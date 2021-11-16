package yong.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import yong.board.api.MovieApiClient;
import yong.board.vo.MovieVO;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class MovieController {


    private final MovieApiClient movieApiClient;

    @GetMapping("/api/v1/movies/{keyword}") //영화제목 keyword
    public List<MovieVO> get(@PathVariable String keyword) {
        return movieApiClient.requestMovie(keyword);
    }
}

