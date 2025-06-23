package pe.business.app.calls.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.business.app.calls.domain.in.SaveCallsUseCase;
import pe.business.app.calls.domain.out.ApiRatesPort;
import pe.business.app.calls.domain.out.CallsPort;
import pe.business.app.calls.infrastructure.api.controller.exception.ServiceException;
import pe.business.app.calls.infrastructure.api.dto.CallRq;
import pe.business.app.calls.infrastructure.api.dto.CallRs;
import pe.business.app.calls.infrastructure.api.mapper.CallHistoryMapper;
import pe.business.app.calls.infrastructure.repository.CallsRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveCallsUseCaseImpl implements SaveCallsUseCase {

    @Autowired
    ApiRatesPort apiRatesPort;

    @Autowired
    CallsPort callsPort;

    @Autowired
    CallHistoryMapper callHistoryMapper;

    @Override
    public Mono<CallRs> saveCall(CallRq callRq) {


        return   apiRatesPort.getRateByApi().flatMap(
                        rt -> {
                            Double calculate = (callRq.number1 + callRq.number2)*(1 + rt.getRate());
                            CallRs callRs = CallRs.builder().number1(callRq.number1)
                                    .number2(callRq.number2)
                                    .result(calculate)
                                    .rate(rt.getRate()).build();

                            return callsPort.save(callHistoryMapper
                                            .toEntity(LocalDateTime.now(), callRq, callRs))
                                    .flatMap(s -> {
                                        callRs.setId(s.getId());
                                        return Mono.just(callRs);
                                    });

                        })
                .onErrorResume(ex -> {
                    log.error("Error in Redis cache {} ");
                    return callsPort.save(callHistoryMapper
                                    .toEntityError(LocalDateTime.now(), callRq, "errorCode:" +ex.getMessage()))
                            .flatMap(sd ->Mono.error(new ServiceException("7000")));
                }  );
    }
}
