package pe.business.app.calls.api.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
public class ErrorMessageT {
    private String code ;
    private List<String> messages ;
}
