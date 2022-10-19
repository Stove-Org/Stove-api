package gg.stove;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import gg.stove.auth.annotation.AdminCheck;
import gg.stove.auth.annotation.LoginCheck;
import gg.stove.domain.user.model.AuthUser;

@RestController
public class HealthCheckApi {

    @GetMapping("/health")
    public String healthCheck() {
        return "Hi! stove-api";
    }

    @LoginCheck
    @GetMapping("/health/user")
    public String userHealthCheck(@AuthenticationPrincipal AuthUser user) {
        return "Hi! I`m user | id: " + user.getId();
    }

    @AdminCheck
    @GetMapping("/health/admin")
    public String adminHealthCheck(@AuthenticationPrincipal AuthUser user) {
        return "Hi! I`m admin | id: " + user.getId();
    }
}
