package pe.business.app.calls.application.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.business.app.calls.api.controller.exception.ServiceException;
import pe.business.app.calls.api.dto.CallHistoryRs;
import pe.business.app.calls.api.dto.CallRq;
import pe.business.app.calls.api.dto.CallRs;

import pe.business.app.calls.api.dto.RateRs;
import pe.business.app.calls.api.gateway.ApiRatesClient;
import pe.business.app.calls.api.mapper.CallHistoryMapper;
import pe.business.app.calls.repository.CallsRepository;

import pe.business.app.calls.repository.RedisRateRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import javax.sql.rowset.serial.SerialException;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
public class CallsServiceImpl implements CallsService {


    @Autowired
    CallsRepository callsRepository;

    @Autowired
    CallHistoryMapper callHistoryMapper;

    @Autowired
    ApiRatesClient apiRatesClient;

    public static final String CODE_EXIST_EMAIL="7005";

    @Override
    public Mono<CallRs> saveCalculateCall(CallRq callRq) {


      return   apiRatesClient.getRateByApi().flatMap(
                rt -> {
                    Double calculate = (callRq.number1 + callRq.number2)*(1 + rt.getRate());
                    CallRs callRs = CallRs.builder().number1(callRq.number1)
                            .number2(callRq.number2)
                            .result(calculate)
                            .rate(rt.getRate()).build();

                    return callsRepository.save(callHistoryMapper
                                    .toEntity(LocalDateTime.now(), callRq, callRs))
                                    .flatMap(s -> {
                                        callRs.setId(s.getId());
                                         return Mono.just(callRs);
                                       });

                })
              .onErrorResume(ex -> {
                  log.error("Error in Redis cache {} ");
                  return callsRepository.save(callHistoryMapper
                              .toEntityError(LocalDateTime.now(), callRq, "errorCode:" +ex.getMessage()))
                          .flatMap(sd ->Mono.error(new ServiceException("7000")));
                }  );
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<CallHistoryRs> findCallHistory(String fromDate, String toDate) {


        return  callsRepository.findAll().map(
                callHistory -> callHistoryMapper.toDto(callHistory));
    }


}
