package board.board_spring.controller;

import board.board_spring.dto.BoardPatchDto;
import board.board_spring.dto.BoardPostDto;
import board.board_spring.dto.BoardResponseDto;
import board.board_spring.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 글 작성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long postBoard(@RequestBody @Validated BoardPostDto boardPostDto) {
        return boardService.createBoard(boardPostDto); // 직접 반환
    }

    // 글 수정
    @PatchMapping("/{boardId}")
    public BoardResponseDto patchBoard(@PathVariable("boardId")Long boardId,
                                           @RequestBody @Validated BoardPatchDto boardPatchDto) {

        return boardService.updateBoard(boardId, boardPatchDto); // 업데이트된 리소스 반환
    }

    // 글 삭제
    @DeleteMapping("/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable("boardId")Long boardId) {
        boardService.deleteBoard(boardId);
    }

    // 상세 조회
    @GetMapping("/{boardId}")
    public BoardResponseDto getBoard(@PathVariable("boardId")Long boardId) {

        return boardService.findByBoardId(boardId);
    }

    // 전체 조회
    @GetMapping
    public Page<BoardResponseDto> getAllBoards(
            // 클라이언트 요청 값을 우리가 사용하도록 변환
            // 몇 페이지인지
            @RequestParam(value = "page", defaultValue = "1") int page,
            // 표시되는 게시글 수
            @RequestParam(value = "size", defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        return boardService.findAllBoards(pageable);
    }


}
