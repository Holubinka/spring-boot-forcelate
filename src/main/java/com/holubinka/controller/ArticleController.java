package com.holubinka.controller;

import com.holubinka.model.Article;
import com.holubinka.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController

public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping(value = "/article")
    public ResponseEntity<Article> create(@RequestBody Article article) {
        return articleService.create(article)
                .map(c -> ResponseEntity.created(getUri("article", c.getId())).body(c))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    private URI getUri(String uri, Long id) {
        return URI.create(String.format("/%s/%s", uri, id));
    }
}
