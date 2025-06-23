package pe.business.app.calls.infrastructure.repository.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table(name = "tbl_call_history")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@AllArgsConstructor @NoArgsConstructor  @Builder
public class CallHistory {

    @Id
    private Integer id;

    @Column(value = "datetime")
    @LastModifiedDate
    private LocalDateTime datetime;

    @NotNull(message = "endpoint not be null")
    private String endpoint;

    @NotNull(message = "request not be null")
    private String request;

    @NotNull(message = "result not be null")
    private String result;

    @Column(value = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(value = "updated_date")
    @CreatedDate
    private LocalDateTime updatedDate;

}
