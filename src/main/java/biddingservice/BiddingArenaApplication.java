package biddingservice;

import biddingservice.dao.CustomBrowseLotMapper;
import biddingservice.factories.SchedulerFactory;
import biddingservice.factories.SenderFactory;
import biddingservice.modules.BiddingArenaDependencyModules;
import biddingservice.resources.BiddingArenaResources;
import biddingservice.services.BiddingArena;
import biddingservice.services.BiddingService;
import biddingservice.gateway.SmsSender;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BiddingArenaApplication extends Application<ApplicationConfiguration> {

    public static void main(String[] args) throws Exception {
        new BiddingArenaApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
    }

    @Override
    public void run(ApplicationConfiguration ApplicationConfiguration, Environment environment) throws Exception {
        log.info("starting bidding-arena...");

        Injector injector = setAndGetInjector(environment, ApplicationConfiguration);
        environment.jersey().register(injector.getInstance(CustomBrowseLotMapper.class));
        environment.jersey().register(injector.getInstance(SmsSender.class));
        environment.jersey().register(injector.getInstance(SchedulerFactory.class));
        environment.jersey().register(injector.getInstance(SenderFactory.class));
        environment.jersey().register(injector.getInstance(BiddingService.class));
        environment.jersey().register(injector.getInstance(BiddingArena.class));
        environment.jersey().register(injector.getInstance(BiddingArenaResources.class));
    }

    private Injector setAndGetInjector(Environment environment, ApplicationConfiguration applicationConfiguration) {
       return Guice.createInjector(guiceModule(environment, applicationConfiguration));
    }

    private Module guiceModule(Environment environment, ApplicationConfiguration applicationConfiguration) {
        log.info("Init guice modules");
        return new BiddingArenaDependencyModules(environment, applicationConfiguration);
    }
}
