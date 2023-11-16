package biddingservice.repositories;

import biddingservice.dao.BidDao;
import biddingservice.entities.Bid;
import biddingservice.entities.Bidder;
import jakarta.inject.Inject;
import java.util.List;


public class BidRepository implements IRepository {
    BidDao bidDao;

    @Inject
    public BidRepository(BidDao bidDao) {
        this.bidDao = bidDao;
    }


    public void save(Bid bid) {
        bidDao.save(bid.getBidId(), bid.getBidderId(), bid.getBidValue(), bid.getLotId());
    }

    public Bidder getMaxBidForLotId(String lotId) {
        return bidDao.getMaxBidForLotId(lotId);
    }

    public List<Bid> getBidsForLotId(String lotId) {
        return bidDao.fetchBidByLotId(lotId);
    }

    public Bid findBid(String bidderId, String lotId) {
        return bidDao.fetchBidByBidderId(bidderId, lotId);
    }

}
