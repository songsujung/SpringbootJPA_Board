package com.example.sj2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardListRcntDTO {
    
    private Long bno;
    
    private String title;

    private String writer;

    private Long replyCount;
    
}
