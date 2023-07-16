package com.example.sj2.controller;

import com.example.sj2.dto.BoardListRcntDTO;
import com.example.sj2.dto.PageRequestDTO;
import com.example.sj2.dto.PageResponseDTO;
import com.example.sj2.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public PageResponseDTO<BoardListRcntDTO> getList(@ParameterObject PageRequestDTO pageRequestDTO) {

        log.info(pageRequestDTO);

        return boardService.listRcnt(pageRequestDTO);
    }





}
