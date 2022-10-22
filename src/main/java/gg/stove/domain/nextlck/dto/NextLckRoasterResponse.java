package gg.stove.domain.nextlck.dto;

import gg.stove.domain.nextlck.entity.NextLckEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NextLckRoasterResponse {

    private Long id;
    private NextLckTeam team;
    private NextLckPlayerResponse top;
    private NextLckPlayerResponse jgl;
    private NextLckPlayerResponse mid;
    private NextLckPlayerResponse bot;
    private NextLckPlayerResponse spt;
    public static NextLckRoasterResponse from(NextLckEntity nextLckEntity) {
        return NextLckRoasterResponse.builder()
            .id(nextLckEntity.getId())
            .team(NextLckTeam.of(nextLckEntity.getTeam()))
            .top(NextLckPlayerResponse.from(nextLckEntity.getTop()))
            .jgl(NextLckPlayerResponse.from(nextLckEntity.getJgl()))
            .mid(NextLckPlayerResponse.from(nextLckEntity.getMid()))
            .bot(NextLckPlayerResponse.from(nextLckEntity.getBot()))
            .spt(NextLckPlayerResponse.from(nextLckEntity.getSpt()))
            .build();
    }
}
