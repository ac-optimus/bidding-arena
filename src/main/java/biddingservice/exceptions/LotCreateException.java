package biddingservice.exceptions;


public class LotCreateException extends Exception implements BiddingArenaException {

    public LotCreateException(String message) {
        super(message);
    }

    public LotCreateException(String message, Throwable cause) {
        super(message, cause);
    }

}
