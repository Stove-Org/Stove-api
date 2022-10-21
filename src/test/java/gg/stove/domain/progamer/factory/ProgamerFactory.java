package gg.stove.domain.progamer.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import gg.stove.domain.progamer.repository.ProgamerRepository;

@Component
public class ProgamerFactory {
    @Autowired
    private ProgamerRepository progamerRepository;

    public ProgamerEntity create() {
        return progamerRepository.save(ProgamerEntity.builder()
            .name("name")
            .nickname("nickname")
            .position(Position.TOP)
            .imgUrl("imgUrl")
            .build());
    }

    public ProgamerEntity create(String name, String nickname, Position position) {
        return progamerRepository.save(ProgamerEntity.builder()
            .name(name)
            .nickname(nickname)
            .position(position)
            .imgUrl("imgUrl")
            .build());
    }
}
