package gg.stove.domain.progamer.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import gg.stove.domain.progamer.dto.UpdateProgamerRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Table(name = "progamers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgamerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "position", nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "image_url")
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public ProgamerEntity(String name, String nickname, Position position, String imageUrl) {
        this.name = name;
        this.nickname = nickname;
        this.position = position;
        this.imageUrl = imageUrl;
    }

    public void update(UpdateProgamerRequest request) {
        String name = request.getName();
        if (name != null) {
            this.name = name;
        }

        String nickname = request.getNickname();
        if (nickname != null) {
            this.nickname = nickname;
        }

        String position = request.getPosition();
        if (position != null) {
            this.position = Position.of(position);
        }

        String imageUrl = request.getImageUrl();
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
    }
}