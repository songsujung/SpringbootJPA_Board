package com.example.sj2.dto;

import java.util.List;
import java.util.stream.IntStream;

import lombok.Data;

// PageResponseDTO를 제네릭<>으로 따로 뺴두면 재활용이 가능
@Data
public class PageResponseDTO<E> {

    // 어떤 자료형 타입이든 받을 수 있게 <E>
    private List<E> dtoList;

    private long totalCount;

    // 자료형타입을 객체로 다루기 위해서 래퍼클래스를 사용 : Integer, String, Long ...
    private List<Integer> pageNums;

    private boolean prev, next;

    private PageRequestDTO pageRequestDTO;

    // 산수 처리를 위해 추가된 변수
    private int page , size , start , end;

    // 생성자
    public PageResponseDTO(List<E> dtoList, long totalCount, PageRequestDTO pageRequestDTO) {
        this.dtoList = dtoList;
        this.totalCount = totalCount;
        this. pageRequestDTO = pageRequestDTO;

        // 산수 처리
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        // 범위에 해당하는 마지막 페이지 번호 구하는 방법
        int tempEnd =(int)(Math.ceil(page/10.0)*10);

        // 시작 페이지 번호
        this.start = tempEnd-9;

        // start가 1이 아닐때 prev 활성화
        this.prev = start != 1;

        // 총 페이지 번호
        int realEnd = (int)(Math.ceil(totalCount/(double)size));

        // 범위에 해당하는 마지막 페이지 번호 저장
        this.end = tempEnd > realEnd ? realEnd : tempEnd;

        // 다음 페이지로 넘어가는 로직
        this.next = (this.end * this.size) < totalCount;

        // start와 end 까지의 범위를 계산해서 pageNums 생성
        // boxed() -> IntStream을 Stream<Integer> 타입으로 변경
        this.pageNums = IntStream.rangeClosed(start, end).boxed().toList();
    }


    
}
