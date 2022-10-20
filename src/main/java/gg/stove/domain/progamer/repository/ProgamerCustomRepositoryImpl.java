package gg.stove.domain.progamer.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gg.stove.domain.progamer.entity.ProgamerEntity;

@Repository
public class ProgamerCustomRepositoryImpl extends QuerydslRepositorySupport implements ProgamerCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ProgamerCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ProgamerEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
