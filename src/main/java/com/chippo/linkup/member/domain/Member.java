package com.chippo.linkup.member.domain;


import com.chippo.linkup.auth.domain.Authority;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static com.chippo.linkup.member.domain.MemberState.ACTIVE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 같은 패키지 내 서브클래서에서만 접근 가능
@SQLDelete(sql = "UPDATE member SET status = 'DELETED' WHERE id = ?") // 삭제 될 때 실행될 SQL 정의
@Where(clause = "status = 'ACTIVE'") // 앤티티에 기본적으로 적용될 SQL 조건절 지정
public class Member {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String loginId;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(nullable = false)
    private LocalDateTime lastLoginDate;

//    @Column(nullable = false)
//    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @Enumerated(value = EnumType.STRING)
    private MemberState status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Member(final Long id, final String loginId, final String nickname) {
        this.id = id;
        this.loginId = loginId;
        this.nickname = nickname;
        this.lastLoginDate = LocalDateTime.now();
        this.authority = Authority.MEMBER;
        this.status = ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public Member(final String loginId, final String nickname) {
        this(null, loginId, nickname);
    }

    public static Member of(final String loginId, final String nickname){
        return new Member(loginId, nickname);
    }
    public boolean isNicknameChanged(final String inputNickname) {
        return !nickname.equals(inputNickname);
    }
}
