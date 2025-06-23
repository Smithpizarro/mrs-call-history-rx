package pe.business.app.calls.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pe.business.app.calls.domain.out.CallsPort;
import pe.business.app.calls.infrastructure.repository.entity.CallHistory;
import pe.business.app.calls.infrastructure.repository.specification.CallHistorySpecification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CallsRepositoryAdapter implements CallsPort {


    private final CallsRepository callsRepository;

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<CallHistory> save(CallHistory callHistory) {

        return callsRepository.save(callHistory);

    }

    public Flux<CallHistory> findCallsByDateRange(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {

        return r2dbcEntityTemplate
                .select(CallHistory.class)
                .matching(CallHistorySpecification.dateTimeBetween(
                        startDate,
                        endDate,
                        pageable))
                .all();
    }

    public Mono<Long> countCallsByDateRange(
            LocalDateTime startDate,
            LocalDateTime endDate) {

        return r2dbcEntityTemplate
                .select(CallHistory.class)
                .matching(CallHistorySpecification.dateTimeBetween(
                        startDate,
                        endDate,
                        Pageable.unpaged()))
                .count();
    }


}
