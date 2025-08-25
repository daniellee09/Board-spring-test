package board.board_spring.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    @Getter // 여기도 Getter를 붙여야 enum 안의 필드가 적용되는 듯
    public enum ExceptionCode {

        BOARD_NOT_FOUND(404, "board not found"); // 404로 교체함

        private int status;
        private String message;

        ExceptionCode(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }

    private ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}
