package biddingservice.dao;

import biddingservice.enums.ProductCategory;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;


public interface ProductDao {

    @SqlUpdate("INSERT INTO PRODUCTS (product_id, name, category, meta) VALUES (:product_id, :name, :category, :meta)")
    void save(@Bind("product_id") String product_id, @Bind("name") String name,
              @Bind("category") ProductCategory category, @Bind("meta") String meta);

}