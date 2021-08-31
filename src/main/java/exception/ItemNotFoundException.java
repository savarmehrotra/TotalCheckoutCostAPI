package exception;

/**
 * Exception thrown when the item id provided doesn't exist in the watch catalog DB
 * */
public class ItemNotFoundException extends Exception {

    public ItemNotFoundException(String message) {

        super(message);
    }
}