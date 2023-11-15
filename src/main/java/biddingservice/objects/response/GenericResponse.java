package biddingservice.objects.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class GenericResponse {
    private int status;
    private ResponseBody response;

    public GenericResponse(int status) {
        this.status = status;
    }

}
