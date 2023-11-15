package biddingservice.exceptions;


public class InvalidUpdateException extends Exception implements BiddingArenaException {

    public InvalidUpdateException(String message) {
        super(message);
    }

    public InvalidUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

}
