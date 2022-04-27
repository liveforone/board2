package board2.board2.repository;

import board2.board2.domain.Board;
import board2.board2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitleContaining(String keyword);

    Board findByUser(User user);
}