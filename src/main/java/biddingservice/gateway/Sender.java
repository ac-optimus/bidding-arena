package biddingservice.gateway;

import java.io.IOException;


public interface Sender extends Gateway {

    public void send(String receiver) throws IOException;

}
