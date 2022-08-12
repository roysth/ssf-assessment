package visa.nus.ssfassessment.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Data {

    private static final Logger logger = LoggerFactory.getLogger(Data.class);

    private String id;
    private String publishedOn;
    private String title;
    private String url;
    private String imageUrl;
    private String body;
    private String tags;
    private String categories;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPublishedOn() {
        return publishedOn;
    }
    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }
    //read the json object, get string and input into list
    public static Data createJson (String json) throws IOException {
        logger.info("createJson data");
        Data d = new Data();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            List<Data> articlesList = new LinkedList<>();
            o.getJsonArray("lineItems")
                    .stream()
                    .map(v -> (JsonObject) v)
                    .forEach((JsonObject v) -> {
                        d.setId(v.getString("id"));
                        d.setPublishedOn(v.getString("published_on"));
                        d.setTitle(v.getString("title"));
                        d.setUrl(v.getString("url"));
                        d.setImageUrl(v.getString("imageurl"));
                        d.setBody(v.getString("body"));
                        d.setTags(v.getString("tags"));
                        d.setCategories(v.getString("categories"));
                        articlesList.add(d);
                    });
        }
        return d;

    }
   
    
}
