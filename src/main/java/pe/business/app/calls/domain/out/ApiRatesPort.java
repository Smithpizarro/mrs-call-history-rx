package pe.business.app.calls.domain.out;

import pe.business.app.calls.infrastructure.api.dto.RateRs;
import reactor.core.publisher.Mono;

public interface ApiRatesPort {

    Mono<RateRs> getRateByApi();
}
