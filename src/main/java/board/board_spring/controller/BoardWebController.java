package board.board_spring.controller;

import board.board_spring.dto.BoardPostDto;
import board.board_spring.dto.BoardPatchDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards")
public class BoardWebController {

    // 게시판 목록 페이지
    @GetMapping
    public String listBoards() {
        return "boards/list";
    }

    // 게시글 상세 페이지
    @GetMapping("/{boardId}")
    public String detailBoard(@PathVariable Long boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "boards/detail";
    }

    // 게시글 작성 페이지
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("boardPostDto", new BoardPostDto());
        return "boards/create";
    }

    // 게시글 수정 페이지
    @GetMapping("/{boardId}/edit")
    public String editForm(@PathVariable Long boardId, Model model) {
        model.addAttribute("boardId", boardId);
        model.addAttribute("boardPatchDto", new BoardPatchDto());
        return "boards/edit";
    }

    // 홈페이지 리다이렉트
    @GetMapping("/")
    public String home() {
        return "redirect:/boards";
    }
}