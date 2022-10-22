package gg.stove.exception;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException() {
        super();
    }
    public DataNotFoundException(String msg) {
        super(msg);
    }
}
