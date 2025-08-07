package board.board_spring.repository;

import board.board_spring.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 레포 선언
public interface BoardRepository extends JpaRepository<Board, Long> {
    // Jpa 상속 받게 해서 기능 확장시키기
    // <Board, Long> 레포에서 사용될 엔티티와 타입 지정
}
