package biddingservice.services;

import biddingservice.Constants;
import biddingservice.entities.Bid;
import biddingservice.entities.Lot;
import biddingservice.enums.FilterCategory;
import biddingservice.exceptions.IllegalBidArgumentException;
import biddingservice.exceptions.InvalidLotIdArgumentException;
import biddingservice.exceptions.InvalidUpdateException;
import biddingservice.exceptions.LotCreateException;
import biddingservice.factories.SenderFactory;
import biddingservice.entities.Product;
import biddingservice.objects.BrowseLot;
import biddingservice.objects.CategoryFilter;
import biddingservice.objects.ProductCategoryFilter;
import biddingservice.objects.TimeBasedFilter;
import biddingservice.repositories.BidRepository;
import biddingservice.repositories.LotRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Slf4j
public class BiddingService {
    private LotRepository lotRepository;
    private BidRepository bidRepository;
    private ObjectMapper objectMapper;
    private ScheduledExecutorService scheduler;
    private SenderFactory senderFactory;

    @Inject
    public BiddingService(LotRepository lotRepository, BidRepository bidRepository,
                          ObjectMapper objectMapper, ScheduledExecutorService scheduler, SenderFactory senderFactory) {
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
        this.objectMapper = objectMapper;
        this.scheduler = scheduler;
        this.senderFactory = senderFactory;
    }

    public Lot createLot(Product product, String vendorId, Double openingPrice, long startTime, long endTime)
            throws LotCreateException {
        try {
            String lotId = generateId();
            String productId = generateId();
            product.setProductId(productId);
            Lot lot = Lot.builder()
                    .lotId(lotId)
                    .productId(productId)
                    .vendorId(vendorId)
                    .openingPrice(openingPrice)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
            lotRepository.save(lot, product);
            long delayTime = (lot.getEndTime() - System.currentTimeMillis())/1000;
            scheduler.schedule(() -> {
                try {
                    closeLot(lot.getLotId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 2 , TimeUnit.SECONDS);
            return lot;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new LotCreateException("Can not create Lot", ex);
        }
    }

    private void closeLot(String lotId) throws IOException {
        Bid maxBidder =  bidRepository.getMaxBidForLotId(lotId);
        String receiver = "receiver";
        senderFactory.getSender(Constants.Gateway.SMS).send(receiver);
        System.out.println("end time reached for lotId: "+lotId);
    }

    public Bid placeBid(String bidderId, Double bidValue, String lotId) throws InvalidUpdateException,
            IllegalBidArgumentException, InvalidLotIdArgumentException, JsonProcessingException {
        validateBid(lotId, bidValue);
        Bid bid = bidRepository.findBid(bidderId);
        if (bid != null) {
            bid.updateBidValue(bidValue);
        } else {
            Bid newBid = Bid.builder()
                    .bidId(generateId())
                    .bidValue(bidValue)
                    .lotId(lotId)
                    .build();
            System.out.println(objectMapper.writeValueAsString(newBid));
            bidRepository.save(newBid);
            return newBid;
        }
        return bid;
    }

    public List<BrowseLot> browseLots() {
        return browseLots(FilterCategory.DEFAULT, null);
    }

    public List<BrowseLot> browseLots(FilterCategory filterCategory, CategoryFilter categoryFilter) {
        List<BrowseLot> lots = Collections.emptyList();
        switch (filterCategory) {
            case TIME_BASED -> lots = lotRepository.fetchLotsByPeriod(((TimeBasedFilter) categoryFilter).getStartTime(),
                    ((TimeBasedFilter) categoryFilter).getEndTime());
            case PRODUCT_CATEGORY -> lots = lotRepository.fetchLotsByProductCategory(((ProductCategoryFilter) categoryFilter)
                    .getProductCategory().toString());
            case DEFAULT -> lots = lotRepository.fetchAll();
            default -> {
            }
        }
        return lots;
    }

    private void validateBid(String lotId, Double bidValue) throws IllegalBidArgumentException,
            InvalidLotIdArgumentException, JsonProcessingException {
        long bidTime = System.currentTimeMillis();
        Lot lot = lotRepository.fetchLot(lotId);
        if (lot==null)
            throw new InvalidLotIdArgumentException("Invalid lot id passed.");
        if (bidTime > lot.getEndTime())
            throw new IllegalBidArgumentException("Trying to place bid for expired Lot.");
        if (bidValue < lot.getOpeningPrice())
            throw new IllegalBidArgumentException("Bid value less than Opening value");
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

}