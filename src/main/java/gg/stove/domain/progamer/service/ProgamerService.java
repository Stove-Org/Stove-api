package gg.stove.domain.progamer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gg.stove.cache.annotation.RedisCacheEvict;
import gg.stove.cache.annotation.RedisCacheable;
import gg.stove.domain.progamer.dto.CreateProgamerRequest;
import gg.stove.domain.progamer.dto.ProgamerViewResponse;
import gg.stove.domain.progamer.dto.UpdateProgamerRequest;
import gg.stove.domain.progamer.entity.ProgamerEntity;
import gg.stove.domain.progamer.repository.ProgamerRepository;
import gg.stove.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProgamerService {

    private final ProgamerRepository progamerRepository;

    @RedisCacheable(key = "ProgamerService.getProgamers", expireSecond = 3600L)
    public List<ProgamerViewResponse> getProgamers() {
        return progamerRepository.findAll().stream()
            .map(ProgamerViewResponse::new)
            .collect(Collectors.toList());
    }

    public ProgamerViewResponse getProgamer(Long progamerId) {
        ProgamerEntity progamer = progamerRepository.findById(progamerId).orElseThrow(
            () -> new DataNotFoundException("progamerId에 해당하는 데이터가 존재하지 않습니다.")
        );
        return new ProgamerViewResponse(progamer);
    }

    @Transactional
    @RedisCacheEvict(key = "ProgamerService.getProgamers")
    public void createProgamer(CreateProgamerRequest request) {
        ProgamerEntity progamerEntity = request.toProgamerEntity();
        progamerRepository.save(progamerEntity);
    }

    @Transactional
    @RedisCacheEvict(key = "ProgamerService.getProgamers")
    public void updateProgamer(Long progamerId, UpdateProgamerRequest request) {
        ProgamerEntity progamerEntity = progamerRepository.findById(progamerId).orElseThrow(() -> new DataNotFoundException("progamerId에 해당하는 데이터가 존재하지 않습니다."));
        progamerEntity.update(request);
    }

    @Transactional
    @RedisCacheEvict(key = "ProgamerService.getProgamers")
    public void deleteProgamer(Long progamerId) {
        ProgamerEntity progamerEntity = progamerRepository.findById(progamerId).orElseThrow(() -> new DataNotFoundException("progamerId에 해당하는 데이터가 존재하지 않습니다."));
        progamerRepository.delete(progamerEntity);
    }
}
