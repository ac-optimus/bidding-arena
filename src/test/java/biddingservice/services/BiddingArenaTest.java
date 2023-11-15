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
import biddingservice.objects.BrowseLot;
import biddingservice.objects.ProductCategoryFilter;
import biddingservice.objects.request.BrowseLotsRequest;
import biddingservice.objects.request.CreateLotRequest;
import biddingservice.objects.request.PlaceBidRequest;
import biddingservice.objects.response.BrowseLotsResponseBody;
import biddingservice.objects.response.CreateLotResponseBody;
import biddingservice.objects.response.PlaceBidResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class BiddingArenaTest {
    @Mock
    BiddingService biddingService;
    @InjectMocks
    BiddingArena biddingArena;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLot() throws LotCreateException {
        when(biddingService.createLot(any(), anyString(), anyDouble(), anyLong(), anyLong())).thenReturn(new Lot("lotId", "productId", "vendorId", Double.valueOf(0), 0L, 0L));
        CreateLotRequest request = CreateLotRequest.builder().endTimeInHours("3").openingPrice(100.0).product(new Product()).vendorId("vendorId").build();
        CreateLotResponseBody result = biddingArena.createLot(request);
        Assertions.assertEquals("lotId", result.getLot().getLotId());
        Assertions.assertEquals("productId", result.getLot().getProductId());
    }

    @Test
    void testBrowseLotsNoCategory() {
        when(biddingService.browseLots()).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));
        when(biddingService.browseLots(any(), any())).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));
        BrowseLotsRequest request = BrowseLotsRequest.builder().build();
        BrowseLotsResponseBody result = biddingArena.browseLots(request);
        Assertions.assertEquals("lotId", result.getLots().get(0).getLotId());
    }

    @Test
    void testBrowseLots() {
        when(biddingService.browseLots()).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));
        when(biddingService.browseLots(any(), any())).thenReturn(List.of(new BrowseLot("lotId", new Product("productId", "name", ProductCategory.FURNITURE, "meta"), "vendorId", Double.valueOf(0), 0L, 0L)));
        BrowseLotsRequest request = BrowseLotsRequest.builder().filterCategory(FilterCategory.PRODUCT_CATEGORY).categoryFilter(new ProductCategoryFilter(ProductCategory.FURNITURE)).build();
        BrowseLotsResponseBody result = biddingArena.browseLots(request);
        Assertions.assertEquals("lotId", result.getLots().get(0).getLotId());
    }

    @Test
    void testPlaceBid() throws InvalidLotIdArgumentException, IllegalBidArgumentException, InvalidUpdateException, JsonProcessingException {
        when(biddingService.placeBid(anyString(), anyDouble(), anyString())).thenReturn(new Bid("bidId", "bidderId", Double.valueOf(0), "lotId"));
        PlaceBidRequest request = PlaceBidRequest.builder().bidderId("bidderId").bidValue(100.0).lotId("lotId").build();
        PlaceBidResponseBody result = biddingArena.placeBid(request);
        Assertions.assertEquals("bidId", result.getBid().getBidId());
    }

}
