package gg.stove.domain.hashtags.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import gg.stove.domain.hashtags.entity.HashtagEntity;

public interface HashtagRepository extends JpaRepository<HashtagEntity, Long> {

    List<HashtagEntity> findAllByHashtagIn(Set<String> hashtags);

}
