package pe.business.app.calls.infrastructure.api.gateway;


import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import pe.business.app.calls.domain.out.RedisRatePort;
import pe.business.app.calls.infrastructure.api.controller.exception.ServiceException;
import pe.business.app.calls.infrastructure.api.dto.RateRs;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@AllArgsConstructor
public class RedisRateClient implements RedisRatePort {

    private final ReactiveRedisOperations<String, String> reactiveRedisOperations;

    @Override
    public Mono<RateRs> save(RateRs rateRs) {
        return reactiveRedisOperations
                .opsForValue()
                .set("rate:" + rateRs.getRate().toString(), rateRs.getRate().toString())
                .map(__ -> rateRs);
    }

    @Override
    public Mono<RateRs> findByKey(String key) {
        return reactiveRedisOperations.opsForValue()
                .get("rate:" + key)
                .map(result -> new RateRs(Double.parseDouble(result)));
    }

    @Override
    public Mono<RateRs> getLast() {
        return reactiveRedisOperations.keys("rate:*")
                .flatMap(key -> reactiveRedisOperations.opsForValue().get(key))
                // If cache is empty, fetch the database for movies
                .switchIfEmpty(Mono.error(new ServiceException("7000")))
                .last()
                .map(result -> new RateRs(Double.parseDouble(result)));
    }

    public Mono<Boolean> expireKey(String key, int duration) {
        return reactiveRedisOperations.expire("rate:" +key,  Duration.ofSeconds(duration));
    }

    public Mono<Boolean> existKey(String key) {
        return reactiveRedisOperations.hasKey("rate:" +key);
    }

}