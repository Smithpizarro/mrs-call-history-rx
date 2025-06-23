package pe.business.app.calls.domain.in;

import pe.business.app.calls.infrastructure.api.dto.CallRq;
import pe.business.app.calls.infrastructure.api.dto.CallRs;
import reactor.core.publisher.Mono;

public interface SaveCallsUseCase {

    Mono<CallRs> saveCall(CallRq callRq);
}
