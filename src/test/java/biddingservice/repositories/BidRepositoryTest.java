package biddingservice.repositories;

import biddingservice.dao.BidDao;
import biddingservice.entities.Bid;
import biddingservice.entities.Bidder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;


class BidRepositoryTest {
    @Mock
    BidDao bidDao;
    @InjectMocks
    BidRepository bidRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        bidRepository.save(new Bid("bidId", "bidderId", 10.0, "lotId"));
    }

    @Test
    void testGetMaxBidForLotId() {
        when(bidDao.getMaxBidForLotId(anyString())).thenReturn(new Bidder("bidderId", "name", "email",
                "number", "address"));
        Bidder result = bidRepository.getMaxBidForLotId("lotId");
        Assertions.assertEquals("bidderId", result.getId());
    }

    @Test
    void testGetBidsForLotId() {
        when(bidDao.fetchBidByLotId(anyString())).thenReturn(List.of(new Bid("bidId", "bidderId", 10.0, "lotId")));
        List<Bid> result = bidRepository.getBidsForLotId("lotId");
        Assertions.assertEquals("bidderId", result.get(0).getBidderId());
    }

    @Test
    void testFindBid() {
        when(bidDao.fetchBidByBidderId(anyString(), anyString())).thenReturn(new Bid("bidId", "bidderId", Double.valueOf(0), "lotId"));
        Bid result = bidRepository.findBid("bidderId", "lotId");
        Assertions.assertEquals("bidId", result.getBidId());
    }

}