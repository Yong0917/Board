package yong.board.api;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yong.board.vo.MovieVO;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieApiClient {

    private final RestTemplate restTemplate;
    private final String CLIENT_ID = "CWKP9jGZMGx7VvBz3I3e";
    private final String CLIENT_SECRET = "z4X4Ld4Yn9";
    private final String OpenNaverMovieUrl_getMovies = "https://openapi.naver.com/v1/search/movie.json?query={keyword}";

    public List<MovieVO> requestMovie(String keyword) {
        final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);
        final HttpEntity<String> entity = new HttpEntity<>(headers);


        String response = restTemplate.exchange(OpenNaverMovieUrl_getMovies, HttpMethod.GET, entity, String.class, keyword).getBody();
        //api호출

        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(response);
            JSONArray item = (JSONArray) obj.get("items");

            List<MovieVO> list = null;
            list = new ArrayList<MovieVO>();        //json을 Vo의 list형태로 맞춰줌

            for (int i = 0; i < item.size(); i++) {
                MovieVO m = new MovieVO();
                JSONObject tmp = (JSONObject) item.get(i);
                String title = (String) tmp.get("title");
                String link = (String) tmp.get("link");
                String image = (String) tmp.get("image");
                String subtitle = (String) tmp.get("subtitle");
                String pubDate = (String) tmp.get("pubDate");
                String director = (String) tmp.get("director");
                String actor = (String) tmp.get("actor");
                String userRating = (String) tmp.get("userRating");
                m.setTitle(title);
                m.setLink(link);
                m.setImage(image);
                m.setSubtitle(subtitle);
                m.setPubDate(pubDate);
                m.setDirector(director);
                m.setActor(actor);
                m.setUserRating(userRating);
                if (m != null)
                    list.add(m);
            }

            return list;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


}
