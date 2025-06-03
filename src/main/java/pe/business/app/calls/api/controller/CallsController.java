package pe.business.app.calls.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.business.app.calls.api.controller.exception.ServiceException;
import pe.business.app.calls.api.dto.CallHistoryRs;
import pe.business.app.calls.api.dto.CallRq;
import pe.business.app.calls.api.dto.CallRs;
import pe.business.app.calls.application.service.CallsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

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
    public Flux<CallHistoryRs> findCallHistory(@RequestParam(name = "fromDate" , required = false) String fromDate,
                                               @RequestParam(name = "toDate" , required = false) String toDate) throws ParseException {
        log.info("Get all  CallHistory");
        if (fromDate!=null && toDate!=null && !isValidDates(fromDate,toDate)){
             return  Flux.error(new ServiceException("7004"));
        }
        Flux<CallHistoryRs> students =  callsService.findCallHistory(fromDate,toDate);
        return  students;

    }

    @PostMapping
    @Operation(summary = "Create Call and Calculate result with rate")
    public Mono<ResponseEntity<CallRs>> saveCalculateCall(
            @Valid @RequestBody CallRq userRq) {

        return callsService.saveCalculateCall(userRq)
                .map(item -> ResponseEntity.status( HttpStatus.CREATED).body(item));

    }

    public static boolean isValidDates(final String fromDate , final String toDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date fromDateTime = formatter.parse(fromDate);
        Date toDateTime = formatter.parse(toDate);

        return (fromDateTime.getTime() <= toDateTime.getTime());
    }


}
