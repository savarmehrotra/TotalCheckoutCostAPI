package exception;

/**
 * Wrapper Exception thrown when there is an AWS side error or connectivity issue while fetching the item data from DynamoDB.
 */
public class DataBaseException extends Exception {

    public DataBaseException(String message, Throwable cause) {

        super(message, cause);
    }
}