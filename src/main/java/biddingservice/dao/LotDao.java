package biddingservice.dao;

import biddingservice.entities.Lot;
import biddingservice.objects.BrowseLot;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;


public interface LotDao {

    @RegisterBeanMapper(Lot.class)
    @SqlQuery("SELECT * FROM LOTS WHERE lot_id = :lot_id")
    Lot fetchLot(@Bind("lot_id") String lotId);

    @SqlUpdate("INSERT INTO LOTS (lot_id, product_id, vendor_id, opening_price, start_time, end_time) " +
            "VALUES (:lot_id, :product_id, :vendor_id, :opening_price, :start_time, :end_time)")
    void save(@Bind("lot_id") String lotId, @Bind("product_id") String productId, @Bind("vendor_id") String vendorId,
             @Bind("opening_price") Double openingPrice, @Bind("start_time") long startTime, @Bind("end_time") long endTime);

    @RegisterBeanMapper(CustomBrowseLotMapper.class)
    @SqlQuery("SELECT lots.lot_id, " +
            "JSON_OBJECT('product_id', products.product_id, 'name', products.name, " +
            "'category', products.category, 'meta', products.meta ) AS product, " +
            "lots.vendor_id, lots.opening_price, " +
            "lots.start_time, lots.end_time " +
            "FROM lots INNER JOIN products ON lots.product_id = products.product_id ")
    List<BrowseLot> fetchLots();

    @RegisterBeanMapper(CustomBrowseLotMapper.class)
    @SqlQuery("SELECT lots.lot_id, " +
            "JSON_OBJECT('product_id', products.product_id, 'name', products.name, " +
            "'category', products.category, 'meta', products.meta ) AS product, " +
            "lots.vendor_id, lots.opening_price, " +
            "lots.start_time, lots.end_time " +
            "FROM lots INNER JOIN products ON lots.product_id = products.product_id " +
            "WHERE products.category = :category")
    List<BrowseLot> fetchLotsByProductCategory(@Bind("category")String productCategory);

    @RegisterBeanMapper(CustomBrowseLotMapper.class)
    @SqlQuery("SELECT lots.lot_id, " +
            "JSON_OBJECT('product_id', products.product_id, 'name', products.name, 'category', products.category, 'meta', products.meta ) AS product,  " +
            "lots.vendor_id, lots.opening_price, lots.start_time, lots.end_time " +
            "FROM lots INNER JOIN products ON lots.product_id = products.product_id " +
            "WHERE lots.start_time>=:start_time AND lots.end_time<=:end_time")
    List<BrowseLot> fetchLotsByPeriod(@Bind("start_time") long startTime, @Bind("end_time") long endTime);

}