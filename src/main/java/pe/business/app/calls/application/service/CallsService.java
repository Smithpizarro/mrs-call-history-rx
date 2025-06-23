package pe.business.app.calls.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pe.business.app.calls.infrastructure.api.dto.CallHistoryRs;
import pe.business.app.calls.infrastructure.api.dto.CallRq;
import pe.business.app.calls.infrastructure.api.dto.CallRs;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CallsService {

     Mono<CallRs> saveCall(CallRq CallRq);

     Mono<Page<CallHistoryRs>> getCallHistory(String fromDate, String toDate , PageRequest pageRequest);

}
