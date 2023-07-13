package com.example.sj2.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.sj2.domain.Board;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    // 100개의 데이터 생성
    @Test
    public void testInsert(){

        for(int i = 0; i < 100; i++){
            Board board = Board.builder()
            .title("title" + i)
            .content("content" + i)
            .writer("sujung"+ (i%10))
            .build();

            boardRepository.save(board);
        }

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

    // 수정 (조회 후, 수정이 이뤄짐)
    @Test
    public void testUpdate() {

        // 조회 후
        long bno = 2L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        // 조회된 값 수정
        board.changeTitle("Update Title");

        boardRepository.save(board);

    }

    // 검색(title로 조회)
    @Test
    public void testSearchTitle(){

        // title에 숫자 1이 있으면 다 조회, 그 결과값을 Board타입의 리스트로 반환
        List<Board> list = boardRepository.findByTitleContaining("1");

        log.info(list);

    }

    // 검색(content로 전체조회 + 페이징처리)
    @Test
    public void testSearchContent(){

        // 첫 번째 페이지(0번째)를 요청하고, 페이지 당 10개의 결과를 반환하도록 페이징 처리를 설정하며, bno를 내림차순으로 정렬
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        // content에 1이 들어간 값을 찾고, pageable로 페이징 처리
        Page<Board> result = boardRepository.findByContentContaining("1", pageable);

        log.info("-----------------------");
        log.info(result);

    }

    // 검색(JPQL사용해서 title로 전체조회)
    @Test
    public void testSearchTitle_jpql(){

        List<Board> list = boardRepository.searchTitle("1");

        log.info("-----------------");
        log.info(list.size());
        log.info(list);
    }

    // 검색(JPQL사용, b.title로 비교해서 bno, title을 조회)
    @Test
    public void testSearchTitle2_jpql(){
        
        List<Object[]> list = boardRepository.searchTitle2("1");

        log.info("-----------------");
        log.info(list.size());
        
        // 배열들의 값 확인 (forEach)
        list.forEach(arr -> log.info(Arrays.toString(arr)));

    }

    // 검색(JPQL사용, b.title로 비교해서 bno, title을 조회 + 페이징추가)
    @Test
    public void testSearchTitle3_jpql(){
        
        Pageable pageable = PageRequest.of(0, 10,  Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.searchTitle3("1", pageable);

        log.info(result);

    }


    // 페이징 + switch문 검색 
    @Test
    public void testSearch1(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.search1("tcw" , "1" , pageable);
   
        log.info(result.getTotalElements());

        result.get().forEach(b -> log.info(b));

    }


    // QueryDsl를 이용한 페이징 처리 2
    // @Test
    // public void testSearch1(){

    //     // 첫번째 페이지를 0으로 설정하고, 한페이지 당 10개의 데이터가 나오도록 반환하고, bno를 내림차순 정렬
    //     Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

    //     boardRepository.search1(pageable);

    // }

    // QueryDsl를 이용한 데이터 검색 1
    // @Test
    // public void testSearch1() {
    //     boardRepository.search1();
    // }
    



}
