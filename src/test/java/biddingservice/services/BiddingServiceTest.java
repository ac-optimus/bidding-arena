package biddingservice.services;

import biddingservice.entities.Bid;
import biddingservice.entities.Lot;
import biddingservice.entities.Product;
import biddingservice.enums.FilterCategory;
import biddingservice.enums.ProductCategory;
import biddingservice.exceptions.IllegalBidArgumentException;
import biddingservice.exceptions.InvalidLotIdArgumentException;
import biddingservice.exceptions.InvalidUpdateException;
import biddingservice.exceptions.LotCreateException;
import biddingservice.factories.SenderFactory;
import biddingservice.gateway.SmsSender;
import biddingservice.objects.BrowseLot;
import biddingservice.objects.ProductCategoryFilter;
import biddingservice.objects.TimeBasedFilter;
import biddingservice.repositories.BidRepository;
import biddingservice.repositories.LotRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static org.mockito.Mockito.*;

class BiddingServiceTest {
    @Mock
    LotRepository lotRepository;
    @Mock
    BidRepository bidRepository;
    @Mock
    ObjectMapper objectMapper;
    @Mock
    ScheduledExecutorService scheduler;
    @Mock
    SenderFactory senderFactory;
    @Mock
    Logger log;
    @InjectMocks
    BiddingService biddingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLot() throws LotCreateException {
        when(bidRepository.getMaxBidForLotId(anyString())).thenReturn(new Bid("bidId", "bidderId", Double.valueOf(0), "lotId"));
        when(senderFactory.getSender(anyString())).thenReturn(new SmsSender(null));
        Lot result = biddingService.createLot(new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(10), 100L, 110L);
        Assertions.assertEquals(result.getEndTime(), 110L);
        Assertions.assertEquals(result.getStartTime(), 100L);
        Assertions.assertEquals(result.getVendorId(), "vendorId");
    }

    @Test
    void testPlaceBid() throws JsonProcessingException, InvalidLotIdArgumentException, IllegalBidArgumentException, InvalidUpdateException {
        when(bidRepository.findBid(anyString())).thenReturn(new Bid("bidId", "bidderId", Double.valueOf(10), "lotId"));
        when(lotRepository.fetchLot("lotId")).thenReturn(Lot.builder()
                .endTime(System.currentTimeMillis()+1000)
                .openingPrice(Double.valueOf(100))
                .build());
        Bid result = biddingService.placeBid("bidderId", Double.valueOf(1000), "lotId");
        Assertions.assertEquals(result.getBidderId(), "bidderId");
        Assertions.assertEquals(result.getLotId(), "lotId");
    }

    @Test
    void testPlaceBidUpdateRequest() throws JsonProcessingException, InvalidLotIdArgumentException, IllegalBidArgumentException, InvalidUpdateException {

        when(lotRepository.fetchLot("lotId")).thenReturn(Lot.builder()
                .endTime(System.currentTimeMillis()+1000)
                .openingPrice(Double.valueOf(100))
                .build());
        Bid result = biddingService.placeBid("bidderId", Double.valueOf(1000), "lotId");
        Assertions.assertEquals(result.getLotId(), "lotId");
    }

    @Test
    void testPlaceBidIllegalBidArgumentExceptionExpiredLot() throws JsonProcessingException, InvalidLotIdArgumentException, IllegalBidArgumentException, InvalidUpdateException {
        when(lotRepository.fetchLot(anyString())).thenReturn(new Lot("lotId", "productId", "vendorId", Double.valueOf(10), 100L, 110L));
        when(bidRepository.findBid(anyString())).thenReturn(new Bid("bidId", "bidderId", Double.valueOf(10), "lotId"));
        assertThrows(IllegalBidArgumentException.class, () -> {
            biddingService.placeBid("bidderId", Double.valueOf(1000), "lotId");
        });
    }

    @Test
    void testPlaceBidIllegalBidArgumentExceptionLowerBidValue() throws JsonProcessingException, InvalidLotIdArgumentException, IllegalBidArgumentException, InvalidUpdateException {
        when(bidRepository.findBid(anyString())).thenReturn(new Bid("bidId", "bidderId", Double.valueOf(10), "lotId"));
        when(lotRepository.fetchLot("lotId")).thenReturn(Lot.builder()
                .endTime(System.currentTimeMillis()+1000)
                .openingPrice(Double.valueOf(100000))
                .build());
        assertThrows(IllegalBidArgumentException.class, () -> {
            biddingService.placeBid("bidderId", Double.valueOf(1000), "lotId");
        });
    }

    @Test
    void testPlaceBidIllegalBidArgumentExceptionInvalidLotId() throws JsonProcessingException, InvalidLotIdArgumentException, IllegalBidArgumentException, InvalidUpdateException {
        when(bidRepository.findBid(anyString())).thenReturn(new Bid("bidId", "bidderId", Double.valueOf(10), "lotId"));
        assertThrows(InvalidLotIdArgumentException.class, () -> {
            biddingService.placeBid("bidderId", Double.valueOf(1000), "lotId");
        });
    }


    @Test
    void testBrowseLots() {
        when(lotRepository.fetchLotsByProductCategory(anyString())).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));
        when(lotRepository.fetchLotsByPeriod(anyLong(), anyLong())).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));
        when(lotRepository.fetchAll()).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));

        List<BrowseLot> result = biddingService.browseLots();
        Assertions.assertEquals(result.get(0).getVendorId(), "vendorId");
    }

    @Test
    void testBrowseLotsTimeBased() {
        when(lotRepository.fetchLotsByProductCategory(anyString())).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));
        when(lotRepository.fetchLotsByPeriod(anyLong(), anyLong())).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));
        when(lotRepository.fetchAll()).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));

        List<BrowseLot> result = biddingService.browseLots(FilterCategory.TIME_BASED, new TimeBasedFilter(0L, 0L));
        Assertions.assertEquals(result.get(0).getLotId(), "lotId");
        Assertions.assertEquals(result.get(0).getProduct().getProductId(), "productId");
    }

    @Test
    void testBrowseLotsProductCategory() {
        when(lotRepository.fetchLotsByProductCategory(anyString())).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));
        when(lotRepository.fetchLotsByPeriod(anyLong(), anyLong())).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));
        when(lotRepository.fetchAll()).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));

        List<BrowseLot> result = biddingService.browseLots(FilterCategory.PRODUCT_CATEGORY, new ProductCategoryFilter(ProductCategory.FURNITURE) );
        Assertions.assertEquals(result.get(0).getLotId(), "lotId");
        Assertions.assertEquals(result.get(0).getProduct().getProductId(), "productId");
    }

}