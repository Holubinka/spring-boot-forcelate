package com.holubinka.dao;

import com.holubinka.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDao extends JpaRepository<Article, Long> {
}
