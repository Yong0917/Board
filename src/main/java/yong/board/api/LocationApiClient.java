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
import yong.board.vo.LocationVO;
import yong.board.vo.MovieVO;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LocationApiClient {

    private final RestTemplate restTemplate;
    private final String CLIENT_ID = "CWKP9jGZMGx7VvBz3I3e";
    private final String CLIENT_SECRET = "z4X4Ld4Yn9";
    private final String OpenNaverLocalUrl_getLocal = "https://openapi.naver.com/v1/search/local.json?query={keyword}&display=10&sort=comment";

    public List<LocationVO> requestLocation(String keyword) {
        final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.

        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);


        String response = restTemplate.exchange(OpenNaverLocalUrl_getLocal, HttpMethod.GET, entity, String.class, keyword).getBody();
        //api호출

        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(response);
            JSONArray item = (JSONArray) obj.get("items");

            List<LocationVO> list = null;
            list = new ArrayList<LocationVO>();        //json을 Vo의 list형태로 맞춰줌

            for (int i = 0; i < item.size(); i++) {

                LocationVO L = new LocationVO();
                JSONObject tmp = (JSONObject) item.get(i);

//                int total = (int) tmp.get("total");
                String category = (String) tmp.get("category");
                String title = (String) tmp.get("title");
                String link = (String) tmp.get("link");
                String description = (String) tmp.get("description");
                String address = (String) tmp.get("address");
                String roadAddress = (String) tmp.get("roadAddress");
//                String mapx = (String) tmp.get("mapy");
//                String mapy = (String) tmp.get("mapx");


//                L.setTotal(total);
                L.setCategory(category);
                L.setTitle(title);
                L.setLink(link);
                L.setDescription(description);
                L.setAddress(address);
                L.setRoadAddress(roadAddress);
//                L.setMapx(mapx);
//                L.setMapy(mapy);

                if (L != null)
                    list.add(L);
            }

            return list;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


}
