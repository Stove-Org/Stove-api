package gg.stove.domain.progamer.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import gg.stove.auth.annotation.AdminCheck;
import gg.stove.domain.progamer.dto.CreateProgamerRequest;
import gg.stove.domain.progamer.dto.ProgamerViewResponse;
import gg.stove.domain.progamer.dto.UpdateProgamerRequest;
import gg.stove.domain.progamer.service.ProgamerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProgamerController {
    private final ProgamerService progamerService;

    @GetMapping("/api/v1/progamers")
    public List<ProgamerViewResponse> getProgamers() {
        return progamerService.getProgamers();
    }

    @AdminCheck
    @PostMapping(value = "/api/v1/progamers")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProgamer(@RequestBody @Valid CreateProgamerRequest request) {
        progamerService.createProgamer(request);
    }

    @AdminCheck
    @PutMapping("/api/v1/progamers/{progamerId}")
    public void updateProgamer(@PathVariable Long progamerId, @RequestBody @Valid UpdateProgamerRequest request) {
        progamerService.updateProgamer(progamerId, request);
    }

    @AdminCheck
    @DeleteMapping("/api/v1/progamers/{progamerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProgamer(@PathVariable Long progamerId) {
        progamerService.deleteProgamer(progamerId);
    }
}
