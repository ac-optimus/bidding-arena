package biddingservice.exceptions;


public class InvalidLotIdArgumentException extends Exception implements BiddingArenaException {

    public InvalidLotIdArgumentException(String message) {
        super(message);
    }

    public InvalidLotIdArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

}
