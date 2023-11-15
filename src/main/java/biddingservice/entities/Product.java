package biddingservice.entities;

import biddingservice.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jdbi.v3.core.mapper.reflect.ColumnName;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Setter
    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private ProductCategory category;

    @JsonProperty("meta")
    private String meta;

}