package biddingservice.objects;

import biddingservice.enums.FilterCategory;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProductCategoryFilter.class, name = "PRODUCT_CATEGORY"),
        @JsonSubTypes.Type(value = TimeBasedFilter.class, name = "TIME_BASED")
})

public interface CategoryFilter {
    
}
