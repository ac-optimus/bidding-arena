package biddingservice.services;

import biddingservice.entities.Bid;
import biddingservice.entities.Lot;
import biddingservice.exceptions.IllegalBidArgumentException;
import biddingservice.exceptions.InvalidLotIdArgumentException;
import biddingservice.exceptions.InvalidUpdateException;
import biddingservice.exceptions.LotCreateException;
import biddingservice.objects.request.PlaceBidRequest;
import biddingservice.objects.request.CreateLotRequest;
import biddingservice.objects.request.BrowseLotsRequest;
import biddingservice.objects.response.BrowseLotsResponseBody;
import biddingservice.objects.response.CreateLotResponseBody;
import biddingservice.objects.response.PlaceBidResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;


/**
 * BiddingService
 */
public class BiddingArena {
    private BiddingService biddingService;

    @Inject
    public BiddingArena(BiddingService biddingService) {
        this.biddingService = biddingService;
    }

    public CreateLotResponseBody createLot(CreateLotRequest request) throws LotCreateException {

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (long) Integer.parseInt(request.getEndTimeInHours()) * 60 * 60 * 1000;
        Lot lot =  biddingService.createLot(request.getProduct(), request.getVendorId(),
                request.getOpeningPrice(),startTime,  endTime);
        return new CreateLotResponseBody(lot);
    }

    public BrowseLotsResponseBody browseLots(BrowseLotsRequest request) {
        if (request.getCategoryFilter() == null) {
            return new BrowseLotsResponseBody(biddingService.browseLots());
        }
        return new BrowseLotsResponseBody(biddingService
                .browseLots(request.getFilterCategory(), request.getCategoryFilter()));
    }

    public PlaceBidResponseBody placeBid(PlaceBidRequest request) throws InvalidUpdateException,
            IllegalBidArgumentException, InvalidLotIdArgumentException, JsonProcessingException {
        Bid bid = biddingService.placeBid(request.getBidderId(), request.getBidValue(), request.getLotId());
        return new PlaceBidResponseBody(bid);
    }

}
