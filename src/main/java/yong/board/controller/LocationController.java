package yong.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import yong.board.api.LocationApiClient;
import yong.board.vo.LocationVO;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class LocationController {


    private final LocationApiClient locationApiClient;

    @GetMapping("/api/v1/locations/{keyword}") //영화제목 keyword
    public List<LocationVO> get(@PathVariable String keyword) {
        return locationApiClient.requestLocation(keyword);
    }
}

