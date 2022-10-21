package gg.stove.domain.progamer.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import gg.stove.cache.annotation.RedisCacheEvict;
import gg.stove.cache.annotation.RedisCacheEvicts;
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

    @RedisCacheable(key = "ProgamerService.getProgamerEntities", expireSecond = 3600L)
    public List<ProgamerEntity> getProgamerEntities() {
        return progamerRepository.findAll();
    }

    @RedisCacheable(key = "ProgamerService.getProgamerEntityMap", expireSecond = 3600L)
    public Map<Long, ProgamerEntity> getProgamerEntityMap() {
        return getProgamerEntities().stream()
            .collect(Collectors.toMap(ProgamerEntity::getId, progamer -> progamer));
    }

    public List<ProgamerViewResponse> getProgamers() {
        return getProgamerEntities().stream()
            .map(ProgamerViewResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    @RedisCacheEvicts(evicts = {
        @RedisCacheEvict(key = "ProgamerService.getProgamerEntities"),
        @RedisCacheEvict(key = "ProgamerService.getProgamerEntityMap"),
    })
    public void createProgamer(CreateProgamerRequest request) {
        ProgamerEntity progamerEntity = request.toProgamerEntity();
        progamerRepository.save(progamerEntity);
    }

    @Transactional
    @RedisCacheEvicts(evicts = {
        @RedisCacheEvict(key = "ProgamerService.getProgamerEntities"),
        @RedisCacheEvict(key = "ProgamerService.getProgamerEntityMap"),
    })
    public void updateProgamer(Long progamerId, UpdateProgamerRequest request) {
        ProgamerEntity progamerEntity = progamerRepository.findById(progamerId).orElseThrow(DataNotFoundException::new);
        progamerEntity.update(request);
    }

    @Transactional
    @RedisCacheEvicts(evicts = {
        @RedisCacheEvict(key = "ProgamerService.getProgamerEntities"),
        @RedisCacheEvict(key = "ProgamerService.getProgamerEntityMap"),
    })
    public void deleteProgamer(Long progamerId) {
        progamerRepository.deleteById(progamerId);
    }
}
