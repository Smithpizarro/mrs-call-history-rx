package pe.business.app.calls.api.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallRs {

 public Integer id;
 private Integer number1;
 private Integer number2;
 private Double rate;
 private Double result;

}
