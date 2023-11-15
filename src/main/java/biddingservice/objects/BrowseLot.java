package biddingservice.objects;

import biddingservice.entities.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jdbi.v3.core.mapper.reflect.ColumnName;


@AllArgsConstructor
@Getter
public class BrowseLot {
    @JsonProperty("lot_id")
    private String lotId;

    @JsonProperty("product")
    @ColumnName("product")
    private Product product;

    @JsonProperty("vendor_id")
    private String vendorId;

    @JsonProperty("opening_price")
    private Double openingPrice;

    @JsonProperty("start_time")
    private long startTime;

    @JsonProperty("end_time")
    private long endTime;

}
