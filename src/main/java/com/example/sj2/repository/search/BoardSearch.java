package com.example.sj2.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.sj2.domain.Board;
import com.example.sj2.dto.BoardListRcntDTO;
import com.example.sj2.dto.PageRequestDTO;
import com.example.sj2.dto.PageResponseDTO;

public interface BoardSearch {


    // 페이징 + switch문 검색
    Page<Board> search1(String searchType , String keyword , Pageable pageable);

    // 검색(댓글 개수가 포함된 리스트 조회)
    Page<Object[]> searchWithRcnt(String searchType , String keyword , Pageable pageable);


    // // 검색(댓글 개수가 포함된 리스트 조회)_DTO로 반환
    // 서비스쪽에서 변환 할 필요 없음
    PageResponseDTO<BoardListRcntDTO> searchDTORcnt (PageRequestDTO requestDTO);

    // 페이징 처리
    // default : 인터페이스 내에서 메서드를 구현할 수 있게 해주는 키워드
    default Pageable makePageable(PageRequestDTO requestDTO) {
        Pageable pageable = PageRequest.of(requestDTO.getPage()-1,
        requestDTO.getSize(),
        Sort.by("bno").descending());

        return pageable;
    }



    // 페이징처리 2
    //Page<Board> search1(Pageable pageable);

    // QueryDsl를 이용한 데이터 검색 1
    //List<Board> search1(); //queryDsl 목록 조회 확인하기 위해

    
    
}
