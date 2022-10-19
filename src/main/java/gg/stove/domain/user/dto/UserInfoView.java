package gg.stove.domain.user.dto;

import java.time.LocalDateTime;

import gg.stove.domain.user.model.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoView {

    private String email;
    private String nickname;
    private LocalDateTime registAt;

    public static UserInfoView from(AuthUser authUser) {
        return new UserInfoView(
            authUser.getEmail(),
            authUser.getNickName(),
            authUser.getCreatedAt()
        );
    }
}
