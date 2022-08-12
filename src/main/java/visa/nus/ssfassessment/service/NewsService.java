package visa.nus.ssfassessment.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import visa.nus.ssfassessment.model.Data;

@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    private static String URL = "https://min-api.cryptocompare.com/data/v2/news/";

    //@Value("${api.key}")
    //private String apikey;

    String apikey = System.getenv("LATEST_NEWS_API_KEY");

    //to form this: https://min-api.cryptocompare.com/data/v2/news/?lang=EN
    public Optional<Data> getArticles () {

        String latestNewsUrl = UriComponentsBuilder.fromUriString(URL)
        .queryParam("lang", "EN")
        .toUriString();
        logger.info(latestNewsUrl);

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("ApiKey", apikey);
            HttpEntity request = new HttpEntity<>(headers);
            resp = template.exchange(
                latestNewsUrl, 
                HttpMethod.GET, 
                request, 
                String.class);
            logger.info(resp.getBody());
            Data articles = Data.createJson(resp.getBody());
            return Optional.of(articles);
        } 
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } 
        return Optional.empty();
    }

    @Autowired
    @Qualifier("newsArticles")
    RedisTemplate<String, Data> redisTemplate;

    //method to save the article in redis
    public void saveArticles (final Data dataList) {
        logger.info("Save DataList >> " +  dataList.getId());
        redisTemplate.opsForValue().set(dataList.getId(), dataList);
    }

    //method to retrieve the article via id
    public Data findById(final String dataId) { 
        logger.info("Find article by ID >> " + dataId);
        Data result = (Data) redisTemplate.opsForValue().get(dataId);
        return result;
    }
}

