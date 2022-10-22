package gg.stove.domain.nextlck.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gg.stove.domain.progamer.entity.ProgamerEntity;
import gg.stove.domain.team.entity.TeamEntity;
import gg.stove.domain.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Table(name = "next_lcks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NextLckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user; //idx

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_id")
    private ProgamerEntity top;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jgl_id")
    private ProgamerEntity jgl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid_id")
    private ProgamerEntity mid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bot_id")
    private ProgamerEntity bot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spt_id")
    private ProgamerEntity spt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public NextLckEntity(UserEntity user, TeamEntity team, ProgamerEntity top, ProgamerEntity jgl, ProgamerEntity mid, ProgamerEntity bot, ProgamerEntity spt) {
        this.user = user;
        this.team = team;
        this.top = top;
        this.jgl = jgl;
        this.mid = mid;
        this.bot = bot;
        this.spt = spt;
    }

    public void update(ProgamerEntity top, ProgamerEntity jungle, ProgamerEntity mid, ProgamerEntity ad, ProgamerEntity supporter) {
        this.top = top;
        this.jgl = jungle;
        this.mid = mid;
        this.bot = ad;
        this.spt = supporter;
    }
}
