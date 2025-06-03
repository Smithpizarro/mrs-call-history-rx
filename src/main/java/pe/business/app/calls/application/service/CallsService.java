package pe.business.app.calls.application.service;

import pe.business.app.calls.api.dto.CallHistoryRs;
import pe.business.app.calls.api.dto.CallRq;
import pe.business.app.calls.api.dto.CallRs;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CallsService {

     Mono<CallRs> saveCalculateCall(CallRq CallRq);

     Flux<CallHistoryRs> findCallHistory(String fromDate, String toDate);

}
