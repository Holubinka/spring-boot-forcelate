package com.holubinka.services;

import com.holubinka.dao.ArticleDao;
import com.holubinka.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Optional<Article> create(Article article) {
        return Optional.of(articleDao.save(article));
    }
}
