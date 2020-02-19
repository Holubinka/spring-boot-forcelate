package com.holubinka.services;

import com.holubinka.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    Optional<Article> create(Article article);
}
