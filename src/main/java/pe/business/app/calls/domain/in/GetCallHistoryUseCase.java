package pe.business.app.calls.domain.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pe.business.app.calls.infrastructure.api.dto.CallHistoryRs;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface GetCallHistoryUseCase {


    Mono<Page<CallHistoryRs>> getCallHistory(LocalDateTime fromDate, LocalDateTime toDate , PageRequest pageRequest);
}
