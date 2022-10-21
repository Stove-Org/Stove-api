package gg.stove.domain.progamer.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
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

    public List<ProgamerViewResponse> getProgamers() {
        return progamerRepository.findAll().stream()
            .map(ProgamerViewResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void createProgamer(CreateProgamerRequest request) {
        ProgamerEntity progamerEntity = request.toProgamerEntity();
        progamerRepository.save(progamerEntity);
    }

    @Transactional
    public void updateProgamer(Long progamerId, UpdateProgamerRequest request) {
        ProgamerEntity progamerEntity = progamerRepository.findById(progamerId).orElseThrow(DataNotFoundException::new);
        progamerEntity.update(request);
    }

    @Transactional
    public void deleteProgamer(Long progamerId) {
        progamerRepository.deleteById(progamerId);
    }
}