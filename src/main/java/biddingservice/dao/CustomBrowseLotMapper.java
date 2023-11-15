package biddingservice.dao;

import biddingservice.entities.Product;
import biddingservice.enums.ProductCategory;
import biddingservice.objects.BrowseLot;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomBrowseLotMapper implements RowMapper<BrowseLot> {
    private ObjectMapper objectMapper;

    @Inject
    public CustomBrowseLotMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Override
    public BrowseLot map(ResultSet resultSet, StatementContext statementContext) throws SQLException {
        Product product = null;
        try {
            product = objectMapper.readValue(resultSet.getString("product"), Product.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new BrowseLot(resultSet.getString("lot_id"),
                product,
                resultSet.getString("vendor_id"),
                Double.parseDouble(resultSet.getString("opening_price")),
                Long.parseLong(resultSet.getString("start_time")),
                Long.parseLong(resultSet.getString("end_time"))
                );
    }

}