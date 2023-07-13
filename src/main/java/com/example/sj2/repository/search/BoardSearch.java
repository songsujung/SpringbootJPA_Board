package com.example.sj2.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.sj2.domain.Board;

public interface BoardSearch {


    // 페이징 + switch문 검색
    Page<Board> search1(String searchType , String keyword , Pageable pageable);

    // 페이징처리
    //Page<Board> search1(Pageable pageable);

    // QueryDsl를 이용한 데이터 검색
    //List<Board> search1(); //queryDsl 목록 조회 확인하기 위해

    
    
}
