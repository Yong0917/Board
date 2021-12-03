package yong.board.api;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yong.board.vo.LocationVO;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LocationApiClient {

    private final RestTemplate restTemplate;

    @Value("${locationApi.ID}")
    private String CLIENT_ID;

    @Value("${locationApi.SECRET}")
    private String CLIENT_SECRET;

    @Value("${locationApi.URL}")
    private String OpenNaverLocalUrl_getLocal;

    public List<LocationVO> requestLocation(String keyword) {
        final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.

        headers.set("X-Naver-Client-Id", CLIENT_ID);        //header에 clientID
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET); //header에 secretKey

        final HttpEntity<String> entity = new HttpEntity<>(headers);


        String response = restTemplate.exchange(OpenNaverLocalUrl_getLocal, HttpMethod.GET, entity, String.class, keyword).getBody(); // Get형식으로 파라미터를 이용해서 호출한다.
        //api호출

        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(response);      //JSONArray형식으로 바꿈
            JSONArray item = (JSONArray) obj.get("items");

            List<LocationVO> list = null;
            list = new ArrayList<LocationVO>();        //json을 Vo의 list형태로 맞춰줌

            for (int i = 0; i < item.size(); i++) {

                JSONObject tmp = (JSONObject) item.get(i);

                //builder 패턴으로 수정      VO랑 맵핑
                LocationVO L = LocationVO.builder()
                        .category((String) tmp.get("category"))
                        .title((String) tmp.get("title"))
                        .link((String) tmp.get("link"))
                        .description((String) tmp.get("description"))
                        .address((String) tmp.get("address"))
                        .roadAddress((String) tmp.get("roadAddress"))
//                        .total((int)tmp.get("total"))
//                        .mapx((String)tmp.get("mapx"))
//                        .mapy((String)tmp.get("mapy"))
                        .build();

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
