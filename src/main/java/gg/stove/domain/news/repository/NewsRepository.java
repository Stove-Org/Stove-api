package gg.stove.domain.news.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import gg.stove.domain.news.entity.NewsEntity;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

}
