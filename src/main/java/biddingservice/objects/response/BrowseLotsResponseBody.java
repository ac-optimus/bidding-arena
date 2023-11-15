package biddingservice.objects.response;

import biddingservice.objects.BrowseLot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;


@AllArgsConstructor
@Getter
public class BrowseLotsResponseBody implements ResponseBody {
    private List<BrowseLot> lots;

}
