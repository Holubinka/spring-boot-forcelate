package com.holubinka.services;

import com.holubinka.dao.ArticleDao;
import com.holubinka.dao.UserDao;
import com.holubinka.model.Article;
import com.holubinka.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Optional<Article> create(Article article, String email) {
        User user = userDao.findByEmail(email).get();
        article.setUser(user);
        return Optional.of(articleDao.save(article));
    }
}
