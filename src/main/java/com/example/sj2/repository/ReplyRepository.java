package com.example.sj2.repository;

import com.example.sj2.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // 조회 (JQPL)
    @Query("select r from Reply r where r.board.bno =:bno")
    Page<Reply> listBoard(@Param("bno") Long bno, Pageable pageable);


}
