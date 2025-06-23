package pe.business.app.calls.infrastructure.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.business.app.calls.infrastructure.api.controller.exception.ServiceException;
import pe.business.app.calls.infrastructure.api.dto.CallHistoryRs;
import pe.business.app.calls.infrastructure.api.dto.CallRq;
import pe.business.app.calls.infrastructure.api.dto.CallRs;
import pe.business.app.calls.application.service.CallsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/calls")
@Validated
public class CallsController {

    @Autowired
    CallsService callsService;

    public static final String CODE_POTENCIAL="7003";

    @GetMapping
    @Operation(summary = "GetAll CallHistory")
    public Mono<Page<CallHistoryRs>> findCallHistory(@RequestParam(name = "fromDate" , required = false) String fromDate,
                                                     @RequestParam(name = "toDate" , required = false) String toDate,
                                                     @Min(1) @RequestParam( name = "page", required = true) Integer page,
                                                     @Min(3) @RequestParam( name = "size", required = true) Integer size) throws ParseException {
        log.info("Get all  CallHistory");
        if (fromDate!=null && toDate!=null && !isValidDates(fromDate,toDate)){
             return  Mono.error(new ServiceException("7004"));
        }

        PageRequest pageRequest = PageRequest.of(page-1, size);

        return callsService.getCallHistory(fromDate, toDate, pageRequest);

    }

    @PostMapping
    @Operation(summary = "Create Call and Calculate result with rate")
    public Mono<ResponseEntity<CallRs>> saveCalculateCall(
            @Valid @RequestBody CallRq userRq) {

        return callsService.saveCall(userRq)
                .map(item -> ResponseEntity.status( HttpStatus.CREATED).body(item));

    }

    public static boolean isValidDates(final String fromDate , final String toDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date fromDateTime = formatter.parse(fromDate);
        Date toDateTime = formatter.parse(toDate);

        return (fromDateTime.getTime() <= toDateTime.getTime());
    }


}
