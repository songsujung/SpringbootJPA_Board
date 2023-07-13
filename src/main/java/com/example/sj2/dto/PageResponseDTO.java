package com.example.sj2.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageResponseDTO<E> {

    // 어떤 자료형 타입이든 받을 수 있게 <E>
    private List<E> dtoList;

    private long totalCount;

    // 자료형타입을 객체로 다루기 위해서 래퍼클래스를 사용 : Integer, String, Long ...
    private List<Integer> pageNum;

    private boolean prev, next;

    private PageRequestDTO pageRequestDTO;

    // 생성자
    public PageResponseDTO(List<E> dtoList, long totalCount, PageRequestDTO pageRequestDTO) {
        this.dtoList = dtoList;
        this.totalCount=totalCount;
        this.pageRequestDTO = pageRequestDTO;
    }


    
}
