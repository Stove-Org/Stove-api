package gg.stove.domain.nextlck.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gg.stove.domain.nextlck.entity.NextLckEntity;
import gg.stove.domain.user.entity.UserEntity;

import static gg.stove.domain.nextlck.entity.QNextLckEntity.nextLckEntity;
import static gg.stove.domain.progamer.entity.QProgamerEntity.progamerEntity;

@Repository
public class NextLckRepositoryImpl extends QuerydslRepositorySupport implements NextLckCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public NextLckRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(NextLckEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<NextLckEntity> findAllWithPlayers() {
        return jpaQueryFactory
            .selectFrom(nextLckEntity)
            .leftJoin(nextLckEntity.progamer, progamerEntity)
            .fetchJoin()
            .fetch();
    }

    @Override
    public List<NextLckEntity> findAllByUserWithPlayers(UserEntity user) {
        return jpaQueryFactory
            .selectFrom(nextLckEntity)
            .where(nextLckEntity.user.eq(user))
            .leftJoin(nextLckEntity.progamer, progamerEntity)
            .fetchJoin()
            .fetch();
    }

    @Override
    public long countDistinctUser() {
        return jpaQueryFactory
            .selectDistinct(nextLckEntity.user)
            .from(nextLckEntity)
            .fetch().size();
    }
}
