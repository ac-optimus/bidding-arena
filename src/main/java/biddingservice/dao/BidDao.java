package biddingservice.dao;

import biddingservice.entities.Bid;
import biddingservice.entities.Bidder;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;


public interface BidDao {

    @RegisterBeanMapper(Bid.class)
    @SqlQuery("SELECT * FROM BIDS WHERE bidder_id= :bidder_id AND lot_id= :lot_id")
    Bid fetchBidByBidderId(@Bind("bidder_id") String bidderId, @Bind("lot_id") String lotId);

    @SqlUpdate("INSERT INTO BIDS (bid_id, bidder_id, bid_value, lot_id) VALUES (:bid_id, :bidder_id, :bid_value, :lot_id)")
    void save(@Bind("bid_id") String bidId, @Bind("bidder_id") String bidderId,
              @Bind("bid_value") Double bidValue, @Bind("lot_id") String lotId);

    @RegisterBeanMapper(Bid.class)
    @SqlQuery("SELECT * FROM BIDS WHERE lot_id= :lot_id")
    List<Bid> fetchBidByLotId(@Bind("lot_id") String lotId);

    @RegisterBeanMapper(Bidder.class)
    @SqlQuery("SELECT bidders.* " +
            "FROM lots " +
            "INNER JOIN bids ON lots.lot_id = bids.lot_id " +
            "INNER JOIN bidders ON bidders.id = bids.bidder_id " +
            "WHERE lots.lot_id = :lot_id " +
            "AND " +
            "bids.bid_value = ( " +
            "    SELECT MAX(bid_value) " +
            "    FROM bids " +
            ")")
    Bidder getMaxBidForLotId(@Bind("lot_id") String lotId);

}