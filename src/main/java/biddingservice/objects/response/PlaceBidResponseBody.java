package biddingservice.objects.response;

import biddingservice.entities.Bid;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PlaceBidResponseBody implements ResponseBody {
    private Bid bid;

}
