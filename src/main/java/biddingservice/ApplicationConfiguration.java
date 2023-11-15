package biddingservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ApplicationConfiguration extends Configuration {

    private String driverUrl;

    @JsonProperty("database")
    @NotNull
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

}
