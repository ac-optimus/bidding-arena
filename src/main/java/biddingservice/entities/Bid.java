package biddingservice.entities;

import biddingservice.exceptions.InvalidUpdateException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class Bid {
    @JsonProperty("bid_id")
    private String bidId;

    @JsonProperty("bidder_id")
    private String bidderId;

    @JsonProperty("bid_value")
    private Double bidValue;

    @JsonProperty("lot_id")
    private String lotId;

    public void updateBidValue(Double value) throws InvalidUpdateException {
        if (value > bidValue)
            bidValue = value;
        else
            throw new InvalidUpdateException("New bid value should be greater than current value.");
    }

}
