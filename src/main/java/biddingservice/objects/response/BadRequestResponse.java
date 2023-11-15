package biddingservice.objects.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BadRequestResponse implements ResponseBody {
    private String message;
    private Object data;

    public BadRequestResponse(String message) {
        this.message = message;
    }

}
