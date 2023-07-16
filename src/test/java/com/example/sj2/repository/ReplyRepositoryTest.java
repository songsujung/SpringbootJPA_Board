package com.example.sj2.repository;

import com.example.sj2.domain.Board;
import com.example.sj2.domain.Reply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    // insert
    // 게시판번호(PK)만 있으면 생성이 가능함(@ManyToOne의 기능)
//    @Test
//    public void insertOne(){
//        Long bno = 100L;
//
//        // reply에 board객체(bnor값)만 설정해주면 댓글이 생성됨
//        Board board = Board.builder().bno(bno).build();
//
//        Reply reply = Reply.builder()
//                .replyText("Reply1")
//                .replyer("송수정")
//                .board(board)
//                .build();
//
//        replyRepository.save(reply);
//    }
//
//    // 여러개의 댓글 insert
//    @Test
//    public void testInsertDumies() {
//        Long[] bnoArr = {99L, 96L, 92L, 84L, 81L};
//
//        for (Long bno : bnoArr) {
//
//            Board board = Board.builder().bno(bno).build();
//
//            for (int i = 0; i < 5; i++) {
//
//                Reply reply = Reply.builder()
//                        .replyText("Reply" + bno + "--" + i)
//                        .replyer("송수정" + i)
//                        .board(board)
//                        .build();
//
//                replyRepository.save(reply);
//
//            }
//        }// end for
//    }

    // 조회 (JQPL)
    @Test
    public void testListBaord(){

        Long bno = 99L;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").ascending());

        Page<Reply> result =replyRepository.listBoard(bno, pageable);

        result.get().forEach(r -> log.info(r));
    }




}
