package pe.business.app.calls.infrastructure.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.business.app.calls.infrastructure.repository.entity.CallHistory;

@Repository
public interface CallsRepository extends ReactiveCrudRepository<CallHistory, Integer> {


}
