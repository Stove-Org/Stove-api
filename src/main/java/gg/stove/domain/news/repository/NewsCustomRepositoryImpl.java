package gg.stove.domain.news.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gg.stove.domain.news.dto.NewsViewResponse;
import gg.stove.domain.news.dto.QNewsViewResponse;
import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.utils.CustomPageImpl;

import static gg.stove.domain.news.entity.QNewsEntity.newsEntity;

@Repository
public class NewsCustomRepositoryImpl extends QuerydslRepositorySupport implements NewsCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public NewsCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(NewsEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<NewsViewResponse> getNewsPage(Pageable pageable) {
        JPQLQuery<NewsEntity> countQuery = jpaQueryFactory
            .selectFrom(newsEntity);

        List<NewsViewResponse> results = countQuery
            .select(
                new QNewsViewResponse(
                    newsEntity.id,
                    newsEntity.headline,
                    newsEntity.linkUrl,
                    newsEntity.imgUrl,
                    newsEntity.uploadedAt,
                    newsEntity.viewCount
                )
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new CustomPageImpl<>(results, pageable, countQuery.fetchCount());
    }
}
