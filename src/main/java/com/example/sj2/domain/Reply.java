package com.example.sj2.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="t_reply1")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "board") // board를 제외하고 ToString, 쿼리만 테스트 값으로 나오게 하기 위해
@Getter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoIncrement
    private Long rno;

    private  String replyText;

    private  String replyFile;

    private String replyer;

    // 하나의 댓글은 하나의 게시글의 포함되게 하기 위해 사용된 코드
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

}


