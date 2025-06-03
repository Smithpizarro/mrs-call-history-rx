package pe.business.app.calls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
@EnableR2dbcAuditing
public class MrsCallHistoryRxApplication {

	public static void main(String[] args) {
		SpringApplication.run(MrsCallHistoryRxApplication.class, args);
	}

}
