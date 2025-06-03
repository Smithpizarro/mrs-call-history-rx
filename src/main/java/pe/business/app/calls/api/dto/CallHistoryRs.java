package pe.business.app.calls.api.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallHistoryRs {

 public Integer id;
 public String datetime;
 public String endpoint;
 public String request;
 public String result;

}
