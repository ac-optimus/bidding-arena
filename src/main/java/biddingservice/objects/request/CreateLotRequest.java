package biddingservice.objects.request;

import biddingservice.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateLotRequest implements Request {
    @JsonProperty("product")
    Product product;

    @JsonProperty("vendor_id")
    String vendorId;

    @JsonProperty("opening_price")
    Double openingPrice;

    @JsonProperty("end_time_in_hours")
    String endTimeInHours;

}
