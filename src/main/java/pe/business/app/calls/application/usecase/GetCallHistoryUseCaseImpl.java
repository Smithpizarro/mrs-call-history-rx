package pe.business.app.calls.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pe.business.app.calls.domain.in.GetCallHistoryUseCase;
import pe.business.app.calls.domain.out.CallsPort;
import pe.business.app.calls.infrastructure.api.dto.CallHistoryRs;
import pe.business.app.calls.infrastructure.api.mapper.CallHistoryMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Slf4j
@Component
@RequiredArgsConstructor
public class GetCallHistoryUseCaseImpl implements GetCallHistoryUseCase {


    @Autowired
    CallsPort callsPort;

    @Autowired
    CallHistoryMapper callHistoryMapper;


    public Mono<Page<CallHistoryRs>> getCallHistory(LocalDateTime
                                                            fromDate, LocalDateTime toDate , PageRequest pageRequest) {

        return callsPort.findCallsByDateRange(
                        fromDate, toDate, pageRequest)
                .collectList()
                .zipWith(callsPort.countCallsByDateRange(fromDate, toDate))
                .map(tuple -> new PageImpl<>(
                        callHistoryMapper.toDtos(tuple.getT1()),
                        pageRequest,
                        tuple.getT2()
                ));
    }

}
