package com.example.sj2.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PageRequestDTO {

    // 필수적인 변수
    private int page = 1;
    private int size = 10;

    private String type, keyword;

    // 3
    public PageRequestDTO(){
    this(1, 10); 
    }

    // 2
    public PageRequestDTO(int page , int size){
    this(page, size, null, null);
    }

    // 1 (먼저입력)
    public PageRequestDTO(int page, int size, String type, String keyword){
        this.page = page <= 0 ? 1 : page;
        this.size = size < 0 || size >= 100 ? 10 : size;
        this.type = type;
        this.keyword = keyword;
    }
    
}
