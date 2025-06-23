package pe.business.app.calls.infrastructure.repository.specification;

import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import org.springframework.data.relational.core.query.Query;
import java.time.LocalDateTime;

public class CallHistorySpecification {

    public static Query dateTimeBetween(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {

        Criteria criteria = Criteria.empty();

        if (startDate != null && endDate != null) {
            criteria = criteria.and("datetime")
                    .between(startDate, endDate);
        } else if (startDate != null) {
            criteria = criteria.and("datetime")
                    .greaterThanOrEquals(startDate);
        } else if (endDate != null) {
            criteria = criteria.and("datetime")
                    .lessThanOrEquals(endDate);
        }

        return Query.query(criteria)
                .with(pageable);
    }
}

