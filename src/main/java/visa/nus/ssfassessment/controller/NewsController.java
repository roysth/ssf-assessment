package visa.nus.ssfassessment.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import visa.nus.ssfassessment.model.Data;
import visa.nus.ssfassessment.service.NewsService;


@Controller
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    
    @Autowired
    private NewsService newsService;

    //to retrieve the list of articles
    @GetMapping("/")
    public String newsPage (Model model) {

        Optional<Data> data = newsService.getArticles();
        model.addAttribute("articleList", data);
        return "displaynews";
    }

    //save the articles
    @PostMapping("/articles")
    public String saveNews (Model model) {
        return "displaynews";
        
    }


   
}
