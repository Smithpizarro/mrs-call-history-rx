package pe.business.app.calls.domain.out;

import org.springframework.data.domain.Pageable;
import pe.business.app.calls.infrastructure.repository.entity.CallHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface CallsPort {

    Mono<CallHistory> save(CallHistory callHistory);

    Mono<Long> countCallsByDateRange(
            LocalDateTime startDate,
            LocalDateTime endDate);

    Flux<CallHistory> findCallsByDateRange(LocalDateTime startDate,
                                           LocalDateTime endDate,
                                           Pageable pageable);
}
