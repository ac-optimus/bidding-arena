package biddingservice.gateway;

import jakarta.inject.Inject;
import org.jdbi.v3.core.Jdbi;


public class MySQL implements Gateway {
    Jdbi jdbi;

    @Inject
    public MySQL(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

}
