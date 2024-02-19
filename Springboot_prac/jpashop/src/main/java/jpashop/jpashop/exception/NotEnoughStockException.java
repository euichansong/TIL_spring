package jpashop.jpashop.exception;

public class NotEnoughStockException extends RuntimeException { // ctrl o >> override 메시지, 예외처리 다 넘겨 주기 위해

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

}
