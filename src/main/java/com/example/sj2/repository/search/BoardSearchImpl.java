package com.example.sj2.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.sj2.domain.Board;
import com.example.sj2.domain.QBoard;
import com.example.sj2.dto.BoardListRcntDTO;
import com.example.sj2.dto.PageRequestDTO;
import com.example.sj2.dto.PageResponseDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    // QuerydslRepositorySupport는 엔티티객체를 사용하는데 (엔티티객체를 담은)생성자 호출을 해줘야 컴파일 에러가 안남
    public BoardSearchImpl() {
        super(Board.class);
    }

    // 페이징 + switch문 검색 
    @Override
    // public List<Board> search1() { : 목록 조회
    public Page<Board> search1(String searchType , String keyword , Pageable pageable) {

        // QBoard.java에서 선언 된 board를 board라는 이름으로 인스턴스 생성
        QBoard board = QBoard.board;

        // JPQL쿼리를 작성하기 위해 JPQLQuery 객체를 생성
        JPQLQuery<Board> query = from(board);

        // 키워드가 not null , searchType이 not null 일 때
        if(keyword !=  null && searchType != null){

            // 문자열 -> 배열
            // tc -> [t,c]
            String[] searchArr = searchType.split("");

            // BooleanBuiler : ()를 우선순위 연산자로 사용하기 위한 코드 
            BooleanBuilder searchBuilder = new BooleanBuilder();

            for (String type : searchArr) {
                switch(type){
                    // 검색 조건
                    case "t" -> searchBuilder.or(board.title.contains(keyword));
                    case "c" -> searchBuilder.or(board.content.contains(keyword));
                    case "w" -> searchBuilder.or(board.writer.contains(keyword));
                }
            } // end for

            // 검색조건을 where절에 추가
            query.where(searchBuilder);

        }// end if

        // getQuerydsl() : Querydls 객체 반환
        // applyPagination(pageable, query) : pageable객체를 사용해서 페이징 처리한 후, query에 적용
        this.getQuerydsl().applyPagination(pageable, query);

        // 목록 데이터 추출
        List<Board> list = query.fetch();

        // 데이터 카운터 추출
        Long count = query.fetchCount();

        log.info(list);
        log.info(count);

        return new PageImpl<>(list, pageable, count);
        
    }

    @Override
    public PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO requestDTO) {
        
        return null;
    }


    // 페이징처리 2
    // @Override
    // public Page<Board> search1(Pageable pageable) {

    //     QBoard board = QBoard.board;

    //     JPQLQuery<Board> query = from(board);

    //     // query where절 : 조건이 title에 1을 포함하는 값
    //     query.where(board.title.contains("1"));

    //     this.getQuerydsl().applyPagination(pageable, query);

    //     List<Board> list = query.fetch();

    //     Long count = query.fetchCount();

    //     log.info(list);
    //     log.info(count);

    //     return null;
        
    // }


    // QueryDsl를 이용한 데이터 검색 1
    // @Override
    // public List<Board> search1() {

    //     QBoard board = QBoard.board;

    //     JPQLQuery<Board> query = from(board);

    //     query.where(board.title.contains("1"));

    //     List<Board> list = query.fetch();

    //     Long count = query.fetchCount();

    //     log.info(list);
    //     log.info(count);

    //     return null;
        
    // }

    


    
}
