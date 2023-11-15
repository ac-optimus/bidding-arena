package biddingservice.repositories;

import biddingservice.dao.LotDao;
import biddingservice.dao.ProductDao;
import biddingservice.entities.Lot;
import biddingservice.entities.Product;
import biddingservice.enums.ProductCategory;
import biddingservice.objects.BrowseLot;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class LotRepositoryTest {
    @Mock
    LotDao lotDao;
    @Mock
    ProductDao productDao;
    @Mock
    ObjectMapper objectMapper;
    @InjectMocks
    LotRepository lotRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchLot() {
        when(lotDao.fetchLot(anyString())).thenReturn(new Lot("lotId", "productId", "vendorId",
                10.0, 1L, 10L));
        Lot result = lotRepository.fetchLot("lotId");
        Assertions.assertEquals("productId", result.getProductId());
    }

    @Test
    void testSave() {
        lotRepository.save(new Lot("lotId", "productId", "vendorId", 10.0, 0L,
                0L), new Product("productId", "name", ProductCategory.FURNITURE, "meta"));
    }

    @Test
    void testFetchLotsByProductCategory() {
        when(lotDao.fetchLotsByProductCategory(anyString())).thenReturn(List.of(new BrowseLot("lotId",
                new Product("productId", "name", ProductCategory.FURNITURE, "meta"),
                "vendorId", 10.0, 1L, 10L)));
        List<BrowseLot> result = lotRepository.fetchLotsByProductCategory("productCategory");
        Assertions.assertEquals("lotId", result.get(0).getLotId());
    }

    @Test
    void testFetchLotsByPeriod() {
        when(lotDao.fetchLotsByPeriod(anyLong(), anyLong())).thenReturn(List.of(new BrowseLot("lotId",
                new Product("productId", "name", ProductCategory.FURNITURE, "meta"),
                "vendorId", 10.0, 1L, 10L)));
        List<BrowseLot> result = lotRepository.fetchLotsByPeriod(0L, 0L);
        Assertions.assertEquals("lotId", result.get(0).getLotId());
    }

    @Test
    void testFetchAll() {
        when(lotDao.fetchLots()).thenReturn(List.of(new BrowseLot("lotId",
                new Product("productId", "name", ProductCategory.FURNITURE, "meta"),
                "vendorId", 10.0, 1L, 10L)));
        List<BrowseLot> result = lotRepository.fetchAll();
        Assertions.assertEquals("lotId", result.get(0).getLotId());
    }

}