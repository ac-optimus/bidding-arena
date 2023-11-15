package biddingservice.objects.request;

import biddingservice.enums.FilterCategory;
import biddingservice.objects.CategoryFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BrowseLotsRequest implements Request {
    @JsonProperty("filter_category")
    private FilterCategory filterCategory;

    @JsonProperty("category_filter")
    private CategoryFilter categoryFilter;

}
