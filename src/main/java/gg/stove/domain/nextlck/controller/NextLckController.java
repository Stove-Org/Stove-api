package gg.stove.domain.nextlck.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import gg.stove.auth.annotation.LoginCheck;
import gg.stove.domain.nextlck.dto.NextLckSaveRequest;
import gg.stove.domain.nextlck.dto.NextLckViewResponse;
import gg.stove.domain.nextlck.dto.ParticipantsCountResponseView;
import gg.stove.domain.nextlck.service.NextLckService;
import gg.stove.domain.user.model.AuthUser;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NextLckController {
    private final NextLckService nextLckService;

    @LoginCheck
    @GetMapping("/api/v1/next_lck")
    public NextLckViewResponse loadNextLck(@AuthenticationPrincipal AuthUser user) {
        return nextLckService.loadNextLck(user.getId());
    }

    @LoginCheck
    @PostMapping("/api/v1/next_lck")
    public void saveNextLck(@AuthenticationPrincipal AuthUser user, @RequestBody NextLckSaveRequest request) {
        nextLckService.saveNextLck(user.getId(), request);
    }

    @LoginCheck
    @DeleteMapping("/api/v1/next_lck")
    public void resetNextLck(@AuthenticationPrincipal AuthUser user) {
        nextLckService.resetNextLck(user.getId());
    }

    @GetMapping("/api/v1/next_lck/participants-count")
    public ParticipantsCountResponseView getParticipantsCount() {
        return nextLckService.getParticipantsCount();
    }
}
