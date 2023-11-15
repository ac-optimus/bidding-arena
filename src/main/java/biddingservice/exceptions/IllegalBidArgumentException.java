package biddingservice.exceptions;


public class IllegalBidArgumentException extends Exception implements BiddingArenaException {

    public IllegalBidArgumentException(String message) {
        super(message);
    }

    public IllegalBidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

}
