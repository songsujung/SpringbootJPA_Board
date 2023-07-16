package com.example.sj2.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import com.example.sj2.domain.QReply;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
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


    // 검색(댓글 개수가 포함된 리스트 조회) (Querydsl Join)
    @Override
    public Page<Object[]> searchWithRcnt(String searchType, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        // leftJoin : "하나의" 왼쪽 테이블과 "여러 개의" 오른쪽 테이블 간의 관계를 처리하기 위해 사용
        // on(reply.board.eq(board)) : reply 엔티티의 board 속성과 Board 엔티티의 board 속성이 같은 경우에만 조인
        query.leftJoin(reply).on(reply.board.eq(board));

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

        query.groupBy(board);

        // Tuple : 특정 쿼리 결과의 행을 나타내며, 여러 개의 필드 값을 그룹화하여 반환(데이터의 집합)
        // countDistinct : 중복 제거해서 카운트
        // => Board 엔티티의 게시물 번호, 제목, 작성자 정보와 함께 reply 엔티티의 중복되지 않은 레코드 수를 선택하여 쿼리 결과로 반환하도록 설정
        JPQLQuery<Tuple> tupleQuery =
                query.select(board.bno , board.title , board.writer , reply.countDistinct());

        // fetch() : JPQLQuery가 데이터베이스로부터 쿼리를 실행하고 실행된 결과를 가져오는 메서드
        // fetch() 메서드로 tupleQuery를 실행하고, 그 결과를 List<Tuple>로 반환
        List<Tuple> tuples = tupleQuery.fetch();

        // 페이징 처리
        // applyPagination() : tupleQuery에 페이징 정보를 적용하는 메서드
        // => Pageable 객체의 페이지 번호, 페이지 크기, 정렬 정보 등의 페이징 정보를 tupleQuery에 적용
        this.getQuerydsl().applyPagination(pageable, tupleQuery);

        // object[]로 변환
        List<Object[]> arrList =
                tuples.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());

        log.info(tuples);

        // fetchCount()로 tupleQuery를 실행하고, 실행된 결과의 행 개수를 long 타입으로 반환
        long count = tupleQuery.fetchCount();

        log.info("count: " + count );

        return new PageImpl<>(arrList, pageable, count);
    }


    // 검색(댓글 개수가 포함된 리스트 조회)_DTO로 반환
    @Override
    public PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO requestDTO) {

        Pageable pageable = makePageable(requestDTO);

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);

        // reply 를 leftjoin -> reply.board == board
        query.leftJoin(reply).on(reply.board.eq(board));

        String keyword = requestDTO.getKeyword();
        String searchType = requestDTO.getType();

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

        query.groupBy(board);

        // Projections.bean() : DTO 객체를 선택하기 위해 사용되며, BoardListRcntDTO 클래스와 해당 클래스의 필드 값을 지정
        // as("replyCount")는 선택한 결과를 replyCount라는 이름으로 지정
        // =>  BoardListRcntDTO를 선택하고, Board 엔티티의 필드 값을 DTO에 매핑하여 쿼리 결과로 반환하도록 설정(DTO 객체로 변환하여 활용가능)
        JPQLQuery<BoardListRcntDTO> listQuery =
                query.select(Projections.bean(
                                BoardListRcntDTO.class,
                                board.bno ,
                                board.title ,
                                board.writer ,
                                reply.countDistinct().as("replyCount")
                        )
                );

        List<BoardListRcntDTO> list = listQuery.fetch();

        // 페이징 처리
        this.getQuerydsl().applyPagination(pageable, query);

        log.info("========================");
        log.info(list);

        // totalCount값 호출
        Long totalCount = listQuery.fetchCount();

        log.info("========================");
        log.info(totalCount);

        return new PageResponseDTO<>(list, totalCount, requestDTO);
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
