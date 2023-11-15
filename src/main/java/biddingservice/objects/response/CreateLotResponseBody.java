package biddingservice.objects.response;

import biddingservice.entities.Lot;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CreateLotResponseBody implements ResponseBody {
    private Lot lot;

}
