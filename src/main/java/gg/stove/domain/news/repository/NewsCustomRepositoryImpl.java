package gg.stove.domain.news.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gg.stove.domain.hashtags.dto.HashtagView;
import gg.stove.domain.hashtags.dto.QHashtagView;
import gg.stove.domain.news.dto.AdminNewsViewResponse;
import gg.stove.domain.news.dto.NewsViewResponse;
import gg.stove.domain.news.dto.QAdminNewsViewResponse;
import gg.stove.domain.news.dto.QNewsViewResponse;
import gg.stove.domain.news.entity.NewsEntity;
import gg.stove.utils.CustomPageImpl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static gg.stove.domain.hashtags.entity.QHashtagEntity.hashtagEntity;
import static gg.stove.domain.news.entity.QNewsEntity.newsEntity;
import static gg.stove.domain.news.entity.QNewsHashtagEntity.newsHashtagEntity;

@Repository
public class NewsCustomRepositoryImpl extends QuerydslRepositorySupport implements NewsCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public NewsCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(NewsEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<AdminNewsViewResponse> getAllNews(Pageable pageable) {
        JPQLQuery<NewsEntity> countQuery = jpaQueryFactory
            .selectFrom(newsEntity);

        List<Long> newsIds = countQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(newsEntity.uploadedAt.desc())
            .select(newsEntity.id)
            .fetch();

        List<AdminNewsViewResponse> results = jpaQueryFactory
            .from(newsEntity)
            .leftJoin(newsEntity.newsHashtagEntities, newsHashtagEntity)
            .leftJoin(newsHashtagEntity.hashtag, hashtagEntity)
            .where(newsEntity.id.in(newsIds))
            .transform(
                groupBy(newsEntity.id).list(
                    new QAdminNewsViewResponse(
                        newsEntity.id,
                        newsEntity.headline,
                        newsEntity.linkUrl,
                        newsEntity.imgUrl,
                        newsEntity.uploadedAt,
                        newsEntity.viewCount,
                        newsEntity.isPublished,
                        list(new QHashtagView(hashtagEntity.id, hashtagEntity.hashtag).skipNulls())
                    )
                )
            );

        return new CustomPageImpl<>(results, pageable, countQuery.fetchCount());
    }

    @Override
    public Page<NewsViewResponse> getPublishedNews(Pageable pageable) {
        JPQLQuery<NewsEntity> countQuery = jpaQueryFactory
            .selectFrom(newsEntity);

        List<Long> newsIds = countQuery
            .where(newsEntity.isPublished.isTrue())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(newsEntity.uploadedAt.desc())
            .select(newsEntity.id)
            .fetch();

        List<NewsViewResponse> results = jpaQueryFactory
            .from(newsEntity)
            .leftJoin(newsEntity.newsHashtagEntities, newsHashtagEntity)
            .leftJoin(newsHashtagEntity.hashtag, hashtagEntity)
            .where(newsEntity.id.in(newsIds))
            .transform(
                groupBy(newsEntity.id).list(
                    new QNewsViewResponse(
                        newsEntity.id,
                        newsEntity.headline,
                        newsEntity.linkUrl,
                        newsEntity.imgUrl,
                        newsEntity.uploadedAt,
                        list(new QHashtagView(hashtagEntity.id, hashtagEntity.hashtag).skipNulls())
                    )
                )
            );

        return new CustomPageImpl<>(results, pageable, countQuery.fetchCount());
    }


    @Override
    public Map<Long, List<HashtagView>> getHashtagViewMapByNewsIds(List<Long> newsIds) {
        return jpaQueryFactory
            .from(newsEntity)
            .leftJoin(newsEntity.newsHashtagEntities, newsHashtagEntity)
            .leftJoin(newsHashtagEntity.hashtag, hashtagEntity)
            .where(newsEntity.id.in(newsIds))
            .transform(
                groupBy(newsEntity.id).as(list(
                    new QHashtagView(hashtagEntity.id, hashtagEntity.hashtag).skipNulls()
                ))
            );
    }
}
