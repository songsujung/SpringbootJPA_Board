package com.example.sj2.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;


// AuditingEntityListener : 엔티티 생성 및 수정 시 자동으로 생성일자와 수정일자를 업데이트
@MappedSuperclass // 테이블로 만들어지지 않게 선언
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class BaseEntity {

    // 생성된 날짜와 시간
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;
		
	// 마지막으로 수정된 날짜와 시간
    @LastModifiedDate
    private LocalDateTime modDate;
    
}
