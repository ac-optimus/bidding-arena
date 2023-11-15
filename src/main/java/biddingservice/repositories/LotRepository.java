package biddingservice.repositories;

import biddingservice.dao.LotDao;
import biddingservice.dao.ProductDao;
import biddingservice.entities.Lot;
import biddingservice.entities.Product;
import biddingservice.objects.BrowseLot;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import java.util.List;


public class LotRepository implements IRepository {
    LotDao lotDao;
    ProductDao productDao;
    ObjectMapper objectMapper;

    @Inject
    public LotRepository(LotDao lotDao, ProductDao productDao, ObjectMapper objectMapper) {
        this.lotDao = lotDao;
        this.productDao = productDao;
        this.objectMapper = objectMapper;
    }

    public Lot fetchLot(String lotId) {
        return lotDao.fetchLot(lotId);
    }

    public void save(Lot lot, Product product) {
        productDao.save(product.getProductId(), product.getName(), product.getCategory(), product.getMeta());
        lotDao.save(lot.getLotId(), lot.getProductId(), lot.getVendorId(),
                lot.getOpeningPrice(), lot.getStartTime(), lot.getEndTime());
    }

    public List<BrowseLot> fetchLotsByProductCategory(String productCategory) {
        return lotDao.fetchLotsByProductCategory(productCategory);
    }

    public List<BrowseLot> fetchLotsByPeriod(long startTime, long endTime) {
        return lotDao.fetchLotsByPeriod(startTime, endTime);
    }

    public List<BrowseLot> fetchAll() {
        return lotDao.fetchLots();
    }

}
