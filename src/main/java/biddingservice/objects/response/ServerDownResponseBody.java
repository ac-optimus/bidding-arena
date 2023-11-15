package biddingservice.objects.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ServerDownResponseBody implements ResponseBody {
    private String message;
    private Object data;

    public ServerDownResponseBody(String message) {
        this.message = message;
    }
}
