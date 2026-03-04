package com.example.board_query.api;

import com.example.board_query.api.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

//                                                     엔티티 클래스, 엔티티 클래스의 @Id 변수의 타입
public interface BoardRepository extends JpaRepository<Board, Long> {
}