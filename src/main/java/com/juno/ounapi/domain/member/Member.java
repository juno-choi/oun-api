package com.juno.ounapi.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;    // kakao, naver, email 등
    @Column(unique = true, nullable = false)
    private String memberId;    // sns의 경우 회원 번호
    private String email;
    private String pw;
    private String nickname;
    private String birth;
    private String gender;  // M, W
    private String profile;
    private String thumbnail;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
