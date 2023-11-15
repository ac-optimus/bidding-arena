package biddingservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class Lot {
    @JsonProperty("lot_id")
    private String lotId;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("vendor_id")
    private String vendorId;

    @JsonProperty("opening_price")
    private Double openingPrice;

    @JsonProperty("start_time")
    private long startTime;

    @JsonProperty("end_time")
    private long endTime;

}
