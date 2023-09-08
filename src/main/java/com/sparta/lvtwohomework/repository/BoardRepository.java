package com.sparta.lvtwohomework.repository;

import com.sparta.lvtwohomework.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderBySaveDateDesc();
}
