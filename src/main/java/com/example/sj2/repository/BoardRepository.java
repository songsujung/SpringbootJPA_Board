package com.example.sj2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sj2.domain.Board;

public interface BoardRepository  extends JpaRepository<Board, Long> {
    
}
