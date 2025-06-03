package pe.business.app.calls.repository;

import pe.business.app.calls.api.dto.RateRs;
import reactor.core.publisher.Mono;

public interface RateRepository {

    Mono<RateRs> save(RateRs rateRs);

    Mono<RateRs> findByKey(String key);

    Mono<RateRs> getLast();

    Mono<Boolean> expireKey(String key, int duration);

    Mono<Boolean> existKey(String key);
}
