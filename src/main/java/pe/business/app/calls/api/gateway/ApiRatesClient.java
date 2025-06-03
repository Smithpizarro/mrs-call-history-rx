package pe.business.app.calls.api.gateway;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pe.business.app.calls.api.controller.exception.ServiceException;
import pe.business.app.calls.api.dto.RateRs;
import pe.business.app.calls.repository.RedisRateRepository;
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
public class ApiRatesClient {

    @Autowired
    private WebClient webClient;

    @Autowired
    RedisRateRepository redisRateRepository;


    public Mono<RateRs> getRateByApi() {
        return this.webClient.get()
                .uri("/e649656e-f9a9-4153-9bf7-8cf22361f1b9")
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
