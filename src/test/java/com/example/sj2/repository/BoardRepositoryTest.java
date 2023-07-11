package com.example.sj2.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sj2.domain.Board;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){

        Board board = Board.builder()
        .title("title")
        .content("content")
        .writer("sujung")
        .build();

        boardRepository.save(board);

    }

    // 조회
    @Test
    public void testRead(){

        Long bno = 2L;

        // Optional : null값이 나올시 오류 나지않게 하기위해 사용
        Optional<Board> result = boardRepository.findById(bno);

        log.info("----------------------");

        Board board = result.orElseThrow(); // 예외나면 () 던짐

        log.info(result.get());

        
    }
}
