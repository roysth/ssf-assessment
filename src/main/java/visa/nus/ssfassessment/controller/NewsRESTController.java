package visa.nus.ssfassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import visa.nus.ssfassessment.model.Data;
import visa.nus.ssfassessment.service.NewsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path = "/news", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsRESTController {

    private static final Logger logger = LoggerFactory.getLogger(NewsRESTController.class);
    public Boolean found;

    @Autowired
    NewsService redisService;

    /*//to save the article
    @PostMapping (consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> saveArticles (@RequestBody String json){
        return ResponseEntity.ok();
    }
    */

    //to get the article
    @GetMapping ("/{dataId}")
    public ResponseEntity<Data> getArticleById (@PathVariable String dataId){
        logger.info("get" + dataId);
        Data article = redisService.findById(dataId);
        JsonObjectBuilder builder = Json.createObjectBuilder()
        .add("id", article.getId())
        .add("published_on", article.getPublishedOn())
        .add("title", article.getTitle())
        .add("url", article.getUrl())
        .add("imageurl", article.getImageUrl())
        .add("body", article.getBody())
        .add("tags", article.getTags())
        .add("categories", article.getCategories());
        JsonObject resp = builder.build();
        return ResponseEntity.ok(article);

        if (null == dataId) {
            String error = "Error: Cannot find news article";
            return ResponseEntity.status(404).badRequest().body(error);
        }

    }
}
