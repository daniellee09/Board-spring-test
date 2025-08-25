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

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // Create
    public Long createBoard(BoardPostDto boardPostDto) {
        Board board = new Board();
        board.setTitle(boardPostDto.getTitle());
        board.setContent(boardPostDto.getContent());

        return boardRepository.save(board).getBoardId();
    }

    // Update
    public Long updateBoard(BoardPatchDto boardPatchDto, Long boardId) {
        Board board = findBoardId(boardId);
        board.setTitle(boardPatchDto.getTitle());
        board.setContent(boardPatchDto.getContent());

        return boardRepository.save(board).getBoardId();
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
