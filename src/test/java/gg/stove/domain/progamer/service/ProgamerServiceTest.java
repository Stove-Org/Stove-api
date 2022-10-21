package gg.stove.domain.progamer.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import gg.stove.domain.progamer.dto.CreateProgamerRequest;
import gg.stove.domain.progamer.dto.ProgamerViewResponse;
import gg.stove.domain.progamer.dto.UpdateProgamerRequest;
import gg.stove.domain.progamer.entity.Position;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import gg.stove.domain.progamer.factory.ProgamerFactory;
import gg.stove.domain.progamer.repository.ProgamerRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

@Transactional
@SpringBootTest
class ProgamerServiceTest {

    @Autowired
    private ProgamerService progamerService;

    @Autowired
    private ProgamerRepository progamerRepository;

    @Autowired
    private ProgamerFactory progamerFactory;

    @Test
    void createProgamer() {
        // given
        CreateProgamerRequest createRequest = CreateProgamerRequest.builder()
            .name("name")
            .nickname("nickname")
            .position("Top")
            .imageUrl("imageUrl")
            .build();

        // when
        progamerService.createProgamer(createRequest);

        // then
        ProgamerEntity progamerEntity = progamerRepository.findAll().get(0);
        then(progamerEntity.getName()).isEqualTo("name");
        then(progamerEntity.getNickname()).isEqualTo("nickname");
        then(progamerEntity.getImageUrl()).isEqualTo("imageUrl");
        then(progamerEntity.getPosition()).isEqualTo(Position.TOP);
    }

    @Test
    void getProgamers() {
        // given
        progamerFactory.create();

        // when
        List<ProgamerViewResponse> progamers = progamerService.getProgamers();

        then(progamers.size()).isEqualTo(1L);
        ProgamerViewResponse progamerViewResponse = progamers.get(0);
        then(progamerViewResponse.getName()).isEqualTo("name");
        then(progamerViewResponse.getNickname()).isEqualTo("nickname");
        then(progamerViewResponse.getPosition()).isEqualTo(Position.of("TOP"));
        then(progamerViewResponse.getImageUrl()).isEqualTo("imageUrl");
    }

    @Test
    void updateProgamer() {
        // given
        ProgamerEntity progamerEntity = progamerFactory.create();
        Long id = progamerEntity.getId();

        UpdateProgamerRequest updateRequest = UpdateProgamerRequest.builder()
            .position("mid")
            .build();

        // when
        progamerService.updateProgamer(id, updateRequest);

        // then
        ProgamerEntity updatedProgamerEntity = progamerRepository.findById(id).get();
        then(updatedProgamerEntity.getName()).isEqualTo("name");
        then(updatedProgamerEntity.getNickname()).isEqualTo("nickname");
        then(updatedProgamerEntity.getPosition()).isEqualTo(Position.of("MiD"));
        then(updatedProgamerEntity.getImageUrl()).isEqualTo("imageUrl");
    }

    @Test
    void deleteProgamer() {
        // given
        ProgamerEntity progamerEntity = progamerFactory.create();
        Long id = progamerEntity.getId();

        // when
        progamerService.deleteProgamer(id);

        // then
        then(progamerRepository.count()).isEqualTo(0);
      }
}