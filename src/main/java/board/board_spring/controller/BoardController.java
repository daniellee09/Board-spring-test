package board.board_spring.controller;

import board.board_spring.dto.BoardPatchDto;
import board.board_spring.dto.BoardPostDto;
import board.board_spring.dto.BoardResponseDto;
import board.board_spring.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Getter
@Setter
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 글 작성
    @PostMapping
    public ResponseEntity<Long> postBoard(@RequestBody @Validated BoardPostDto boardPostDto) {
        Long boardId = boardService.createBoard(boardPostDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(boardId);
    }

    // 글 수정
    @PatchMapping("/{boardId}")
    public ResponseEntity<Long> patchBoard(@PathVariable("boardId")Long boardId,
                                           @RequestBody @Validated BoardPatchDto boardPatchDto) {
        boardService.updateBoard(boardPatchDto, boardId);
        return ResponseEntity.status(HttpStatus.OK).body(boardId);
    }

    // 글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable("boardId")Long boardId) {
        boardService.deleteBoard(boardId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 상세 조회
    @GetMapping("/{boardId}")
    public ResponseEntity getBoard(@PathVariable("boardId")Long boardId) {
        BoardResponseDto boardResponseDto = boardService.findByBoardId(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<Page<BoardResponseDto>> getAllBoards(
            // 클라이언트 요청 값을 우리가 사용하도록 변환
            // 몇 페이지인지
            @RequestParam(value = "page", defaultValue = "1") int page,
            // 표시되는 게시글 수
            @RequestParam(value = "size", defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<BoardResponseDto> boards = boardService.findAllBoards(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }


}
