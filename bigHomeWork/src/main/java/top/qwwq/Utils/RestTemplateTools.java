package top.qwwq.Utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateTools {
    private final RestTemplate restTemplate;
    // 现代的springBoot你可以不用显示写 @Autowired 更优雅
    public RestTemplateTools(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public JSONArray getDataPost(String url, JSONObject data){
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 整合数据
        HttpEntity<JSONObject> request = new HttpEntity<>(data,headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url,request, String.class);
        // 合成Json数组
        JSONArray jsonArray = new JSONArray();
        try{
            jsonArray = JSONArray.parseArray(result.getBody());
        }catch (JSONException e){
            JSONObject jsonObject = JSONObject.parseObject(result.getBody());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
