package com.example.sj2.service;

import com.example.sj2.dto.BoardListRcntDTO;
import com.example.sj2.dto.PageRequestDTO;
import com.example.sj2.dto.PageResponseDTO;
import jakarta.transaction.Transactional;

// 트랙잭션 : "모두 성공해야만 반영" 또는 "하나라도 실패하면 롤백"
@Transactional
public interface BoardService {

    // 댓글 개수를 포함한 리스트 목록
    // PageResponseDTO<>으로 만들어 둬서 재활용이 가능해짐
    PageResponseDTO<BoardListRcntDTO> listRcnt(PageRequestDTO pageRequestDTO);
}
