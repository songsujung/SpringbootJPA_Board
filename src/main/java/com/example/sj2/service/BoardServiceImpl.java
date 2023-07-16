package com.example.sj2.service;

import com.example.sj2.dto.BoardListRcntDTO;
import com.example.sj2.dto.PageRequestDTO;
import com.example.sj2.dto.PageResponseDTO;
import com.example.sj2.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{


    private final BoardRepository boardRepository;

    @Override
    public PageResponseDTO<BoardListRcntDTO> listRcnt(PageRequestDTO pageRequestDTO) {

        log.info("----------------------");
        log.info(pageRequestDTO);

        return boardRepository.searchDTORcnt(pageRequestDTO);

    }
}
