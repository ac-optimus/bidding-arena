package biddingservice.repositories;

import biddingservice.dao.BidDao;
import biddingservice.entities.Bid;
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
        when(bidDao.getMaxBidForLotId(anyString())).thenReturn(new Bid("bidId", "bidderId", Double.valueOf(0), "lotId"));
        Bid result = bidRepository.getMaxBidForLotId("lotId");
        Assertions.assertEquals("bidId", result.getBidId());
    }

    @Test
    void testGetBidsForLotId() {
        when(bidDao.fetchBidByLotId(anyString())).thenReturn(List.of(new Bid("bidId", "bidderId", 10.0, "lotId")));
        List<Bid> result = bidRepository.getBidsForLotId("lotId");
        Assertions.assertEquals("bidderId", result.get(0).getBidderId());
    }

    @Test
    void testFindBid() {
        when(bidDao.fetchBidByBidderId(anyString())).thenReturn(new Bid("bidId", "bidderId", Double.valueOf(0), "lotId"));
        Bid result = bidRepository.findBid("bidderId");
        Assertions.assertEquals("bidId", result.getBidId());
    }

}