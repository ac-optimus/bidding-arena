package biddingservice.modules;

import biddingservice.ApplicationConfiguration;
import biddingservice.dao.BidDao;
import biddingservice.dao.LotDao;
import biddingservice.dao.ProductDao;
import biddingservice.dao.CustomBrowseLotMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import okhttp3.OkHttpClient;
import org.jdbi.v3.core.Jdbi;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class BiddingArenaDependencyModules extends AbstractModule {
    private Environment environment;
    private ApplicationConfiguration applicationConfiguration;

    public BiddingArenaDependencyModules(Environment environment, ApplicationConfiguration applicationConfiguration) {
        this.environment = environment;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    protected void configure() {
        // add new bindings
    }
    @Provides
    @Singleton
    private Jdbi getJdbi(CustomBrowseLotMapper customBrowseLotMapper) {
        JdbiFactory factory = new JdbiFactory();
        Jdbi jdbi= factory.build(environment, applicationConfiguration.getDataSourceFactory(), "mysql");
        jdbi.registerRowMapper(customBrowseLotMapper);
        return jdbi;
    }

    @Provides
    @Singleton
    private LotDao getLotDao(Jdbi jdbi) {
        return jdbi.onDemand(LotDao.class);
    }


    @Provides
    @Singleton
    private ProductDao getProductDao(Jdbi jdbi) {
        return jdbi.onDemand(ProductDao.class);
    }


    @Provides
    @Singleton
    private BidDao getBidDao(Jdbi jdbi) {
        return jdbi.onDemand(BidDao.class);
    }

    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    @Provides
    @Singleton
    public OkHttpClient getOkhttpClient() {
        return new OkHttpClient();
    }


    @Provides
    @Singleton
    public ScheduledExecutorService provideScheduledExecutorService() {
        return Executors.newScheduledThreadPool(1);
    }
}
