package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value="myCookie", required = false) String cookie) {
        log.info("request={}", request);        //request=org.apache.catalina.connector.RequestFacade@3800ac47
        log.info("response={}", response);      //response=org.apache.catalina.connector.ResponseFacade@6034b23d
        log.info("httpMethod={}", httpMethod);  //httpMethod=GET
        log.info("locale={}", locale);          //locale=ko_KR
        log.info("headerMap={}", headerMap);    //headerMap={host=[localhost:8080], connection=[keep-alive], sec-ch-ua=["Chromium";v="116", "Not)A;Brand";v="24", "Google Chrome";v="116"], sec-ch-ua-mobile=[?0], sec-ch-ua-platform=["Windows"], upgrade-insecure-requests=[1], user-agent=[Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36], accept=[text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7], sec-fetch-site=[none], sec-fetch-mode=[navigate], sec-fetch-user=[?1], sec-fetch-dest=[document], accept-encoding=[gzip, deflate, br], accept-language=[ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7], cookie=[JSESSIONID=7EACCFF0CCB074BEF481999AC64102F8]}
        log.info("header host={}", host);       //header host=localhost:8080
        log.info("myCookie={}", cookie);        //myCookie=null

        return "ok";

    }
}
