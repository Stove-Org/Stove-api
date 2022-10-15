package gg.stove;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import gg.stove.auth.domain.AuthUser;

@RestController
public class HealthCheckApi {

    @GetMapping("/health")
    public String healthCheck() {
        return "Hi! stove-api";
    }

    @Secured("ROLE_USER")
    @GetMapping("/health/user")
    public String userHealthCheck(@AuthenticationPrincipal AuthUser user) {
        return "Hi! I`m user | id: " + user.getId();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/health/admin")
    public String adminHealthCheck(@AuthenticationPrincipal AuthUser user) {
        return "Hi! I`m admin | id: " + user.getId();
    }
}
