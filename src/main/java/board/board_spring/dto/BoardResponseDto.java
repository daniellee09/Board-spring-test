package board.board_spring.dto;

import board.board_spring.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@AllArgsConstructor
public class BoardResponseDto {

    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 정적 팩토리 메서드 추가
    public static BoardResponseDto findFromBoard(Board board) {
        return new BoardResponseDto(
                board.getBoardId(),
                board.getTitle(),
                board.getContent(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }

    // 날짜 포맷팅 메서드
    public String getFormattedCreatedAt() {
        if (createdAt == null) return "";
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getFormattedUpdatedAt() {
        if (updatedAt == null) return "";
        return updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // 상대적 시간 표시 메서드
    public String getRelativeCreatedTime() {
        if (createdAt == null) return "";
        
        LocalDateTime now = LocalDateTime.now();
        long minutes = java.time.Duration.between(createdAt, now).toMinutes();
        
        if (minutes < 1) return "방금 전";
        if (minutes < 60) return minutes + "분 전";
        
        long hours = minutes / 60;
        if (hours < 24) return hours + "시간 전";
        
        long days = hours / 24;
        if (days < 7) return days + "일 전";
        
        return getFormattedCreatedAt();
    }

}
