package board.board_spring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardPatchDto {

    private String title;

    private String content;

    // null 및 빈 문자열 체크 메서드
    // 제목용
    public boolean hasTitle() {
        return title != null && !title.trim().isEmpty();
    }

    // 내용
    public boolean hasContent() {
        return content != null & !content.trim().isEmpty();
    }

}
