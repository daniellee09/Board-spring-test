package board.board_spring.service;

import board.board_spring.dto.BoardPatchDto;
import board.board_spring.dto.BoardPostDto;
import board.board_spring.dto.BoardResponseDto;
import board.board_spring.entity.Board;
import board.board_spring.exception.BusinessLogicException;
import board.board_spring.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional // 스프링의 트랜잭션 관리 어노테이션, 작업 실패할 경우 롤백해줌
public class BoardService {
    private final BoardRepository boardRepository;

    // Create
    public Long createBoard(BoardPostDto boardPostDto) {

        Board board = Board.postFrom(boardPostDto);

        return boardRepository.save(board).getBoardId();
    }

    // Update
    public BoardResponseDto updateBoard(Long boardId, BoardPatchDto boardPatchDto) {
        Board board = findBoardId(boardId);
        board.updateFrom(boardPatchDto); // 이러면 Entity가 업데이트 처리

        // Transactional로 자동 저장된다고 하네..save 따로 처리 필요 없음
        return BoardResponseDto.findFromBoard(board); // 전체 정보 반환
    }

    // Delete
    public void deleteBoard(Long boardId) {
        findBoardId(boardId);
        boardRepository.deleteById(boardId);
    }

    // Read
    // 게시글 상세 조회
    public BoardResponseDto findByBoardId(Long boardId) {
        Board board = findBoardId(boardId);
        return BoardResponseDto.findFromBoard(board);
    }

    // 전체 게시글 조회, Pagination
    public Page<BoardResponseDto> findAllBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        return boards.map(BoardResponseDto::findFromBoard); // 변환기 느낌쓰
        // boards의 내용을 BoardResponseDto의 FindFromBoard를 참조해서 변환해라
    }

    // 게시글 아이디로 찾기
    public Board findBoardId(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(BusinessLogicException.ExceptionCode.BOARD_NOT_FOUND));
    }


}
