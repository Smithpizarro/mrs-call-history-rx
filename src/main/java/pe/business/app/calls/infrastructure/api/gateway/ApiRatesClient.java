package pe.business.app.calls.infrastructure.api.gateway;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pe.business.app.calls.domain.out.ApiRatesPort;
import pe.business.app.calls.infrastructure.api.controller.exception.ServiceException;
import pe.business.app.calls.infrastructure.api.dto.RateRs;
import pe.business.app.calls.infrastructure.config.ParametersProperties;
import reactor.core.publisher.Mono;

/**
 * Cliente Feign para interactuar con el servicio comunes-command-api. Permite
 * enviar solicitudes
 * para guardar el seguimiento de la escala a través de una API externa.
 *
 * @project cp-api-bs-tramiteyrectificacion-command
 * @autor Fernando Tanta
 * @date 22/08/2024
 */
@AllArgsConstructor
@Component
@Slf4j
public class ApiRatesClient implements ApiRatesPort {

    @Autowired
    WebClient webClient;

    @Autowired
    RedisRateClient redisRateRepository;

    @Autowired
    ParametersProperties parametersProperties;

    public Mono<RateRs> getRateByApi() {
        return this.webClient.get()
                .uri(parametersProperties.getPath())
                .retrieve()
                .bodyToMono(RateRs.class)
                .flatMap(ft -> redisRateRepository.save(ft)
                        .flatMap(ftt ->redisRateRepository.expireKey(ftt.getRate().toString(),360))
                        .then(Mono.just(ft)))
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getStatusCode().value() == 500 ?
                                 redisRateRepository.getLast():
                                 Mono.error(new ServiceException("7002")));

    }



}
