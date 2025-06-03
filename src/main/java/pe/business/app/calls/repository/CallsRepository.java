package pe.business.app.calls.repository;


import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.business.app.calls.repository.entity.CallHistory;

@Repository
public interface CallsRepository extends ReactiveCrudRepository<CallHistory, Integer> {


}
