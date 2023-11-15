package biddingservice.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeBasedFilter implements CategoryFilter {
    @JsonProperty("start_time")
    private long startTime;

    @JsonProperty("end_time")
    private long endTime;

}
