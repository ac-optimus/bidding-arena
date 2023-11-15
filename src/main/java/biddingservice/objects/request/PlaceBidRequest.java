package biddingservice.objects.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceBidRequest implements Request {
    @JsonProperty("bidder_id")
    private String bidderId;

    @JsonProperty("bid_value")
    private Double bidValue;

    @JsonProperty("lot_id")
    private String lotId;

}
