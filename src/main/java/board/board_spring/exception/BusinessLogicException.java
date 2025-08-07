package board.board_spring.exception;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException {

    public enum ExceptionCode {

        BOARD_NOT_FOUND(400, "board not found");

        @Getter
        private int status;
        @Getter
        private String message;

        ExceptionCode(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }

    @Getter
    private ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}
