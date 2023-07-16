package com.example.sj2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sj2.domain.Board;
import com.example.sj2.repository.search.BoardSearch;

public interface BoardRepository  extends JpaRepository<Board, Long>, BoardSearch{

    // 검색(title로 조회)
    // title을 찾고, 결과 값을 Board타입의 리스트로 반환 - 방식1
    List<Board> findByTitleContaining(String title);

    // 검색(content로 전체조회)
    // content를 찾고, 결과 값을 Board타입의 페이지로 반환(Pageable을 사용하면 Page로 반환해야함)
    // 페이징처리까지 더해 content를 검색 - 방식2
    Page<Board> findByContentContaining(String content, Pageable pageable);

    // 검색(JPQL사용해서 title로 전체조회)
    // : JPQL? 어노테이션을 사용해서 조건을 달아 값을 찾는 방식(항상 Param을 넣어 줘야됨)
    // board를 b라는 별칭을 지정, b에 있는 title(b.title)과 파라미터값을 비교해서 완전히 같은 값만 조회
    // @Query("select b from Board b where b.title = :title ") // = :파라미터값 <- 이형식 지켜야함
    @Query("select b from Board b where b.title like %:title%") // like는 포함되기만 하면 조회됨
    List<Board> searchTitle(@Param("title") String title);
    
    // 검색(JPQL사용, b.title로 비교해서 bno, title을 조회)
    @Query("select b.bno,b.title from Board b where b.title like %:title%")
    List<Object[]> searchTitle2(@Param("title") String title);

    // 검색(JPQL사용, b.title로 비교해서 bno, title을 조회 + 페이징추가)
    @Query("select b.bno,b.title from Board b where b.title like %:title%")
    Page<Object[]> searchTitle3(@Param("title") String title, Pageable pageable);

    // 댓글 갯수가 포함된 리스트 조회
    // b.bno, b.title, b.writer 3개가 Object의 배열로
    // JQPL : 조회는 가능하지만 수정은 안됨 (Querydsl사용해야함)
    @Query("select b.bno, b.title, b.writer , count(r) from Board b left outer join Reply r on r.board = b group by b order by b.bno desc")
    List<Object[]> getListWithRcnt();
    
    
}
