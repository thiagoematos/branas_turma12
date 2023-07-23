package turma12.branas.exceptions;

public class InvalidDistanceException extends RuntimeException {
    public InvalidDistanceException(Integer distance) {
        super("Invalid distance: " + distance);
    }
}
