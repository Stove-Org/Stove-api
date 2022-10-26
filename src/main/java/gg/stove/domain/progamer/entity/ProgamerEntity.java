package gg.stove.domain.progamer.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import gg.stove.config.SetAttributeConverter;
import gg.stove.domain.progamer.dto.CareerDto;
import gg.stove.domain.progamer.dto.UpdateProgamerRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Table(name = "progamers")
@EqualsAndHashCode(of = {"id"})
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

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Embedded
    private Career career;

    @Column(name = "img_url")
    private String imgUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Convert(converter = SetAttributeConverter.class)
    @Column(columnDefinition = "json")
    private Set<String> alias = new HashSet<>();

    @Builder
    public ProgamerEntity(String name, String nickname, Position position, LocalDate birthday, Career career, String imgUrl, Set<String> alias) {
        this.name = name;
        this.nickname = nickname;
        this.position = position;
        this.birthday = birthday;
        this.career = career;
        this.imgUrl = imgUrl;
        this.alias = alias;
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

        String imgUrl = request.getImgUrl();
        if (imgUrl != null) {
            this.imgUrl = imgUrl;
        }

        LocalDate birthday = request.getBirthday();
        if (birthday != null) {
            this.birthday = birthday;
        }

        CareerDto career = request.getCareer();
        if (career != null) {
            this.career = career.toEntity();
        }

        Set<String> alias = request.getAlias();
        if (alias != null) {
            this.alias = alias;
        }
    }
}
