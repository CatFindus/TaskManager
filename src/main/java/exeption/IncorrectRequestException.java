package exeption;

public class IncorrectRequestException extends Exception {
    public IncorrectRequestException(String incorrectBodyOfRequest) {
        super(incorrectBodyOfRequest);
    }

    public IncorrectRequestException() {
    }
}
