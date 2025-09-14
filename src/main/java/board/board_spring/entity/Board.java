package board.board_spring.entity;

import board.board_spring.dto.BoardPatchDto;
import board.board_spring.dto.BoardPostDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA용, 외부 직접 생성 방지
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String title;

    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // private 생성자
    private Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Create
    // Entity가 Dto를 받아서 생성 , Dto는 데이터 전송에만 집중해야함
    public static Board postFrom(BoardPostDto postDto) {
        return Board.createBoard(postDto.getTitle(), postDto.getContent());
    }

    // 정적 팩토리 메서드 - 생성 의도가 명확해서 좋은 듯
    // 용도? Entity 자체의 생성과 도메인 규칙 관리
    public static Board createBoard(String title, String content) {
        return new Board(title, content);
    }

    // Update
    // 부분 수정
    public void updateFrom(BoardPatchDto patchDto) {

        boolean isUpdated = false; // 업데이트 여부

        if(patchDto.hasTitle()) {
            validateTitle(patchDto.getTitle());
            this.title = patchDto.getTitle();
            isUpdated = true;
        }

        if(patchDto.hasContent()) {
            validateContent(patchDto.getContent());
            this.content = patchDto.getContent();
            isUpdated = true;
        }

        // 실제 변경이 있었을 경우에만 수정 시간을 갱신
        if (isUpdated) {
            this.updatedAt = LocalDateTime.now();
        }
    }

    // 전체 수정
    public void updateBoard(String title, String content) {
        validateTitle(title);
        validateContent(content);
        this.title = title;
        this.content = content;
    }

    // 업데이트 검증 로직
    // 제목 검증
    private static void validateTitle(String title) {
        if(title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목 입력은 필수입니다."); // 잘못된 인수인지 판단
        }
        if(title.length() > 100) {
            throw new IllegalArgumentException("제목은 100자를 초과할 수 없습니다.");
        }
    }

    // 내용 검증
    private static void validateContent(String content) {
        if(content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("내용 입력은 필수입니다.");
        }
        if(content.length() > 8000) {
            throw new IllegalArgumentException("내용은 8000자를 초과할 수 없습니다.");
        }
    }


}
