package pe.business.app.calls.application.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.business.app.calls.domain.in.GetCallHistoryUseCase;
import pe.business.app.calls.domain.in.SaveCallsUseCase;
import pe.business.app.calls.domain.out.ApiRatesPort;
import pe.business.app.calls.infrastructure.api.controller.exception.ServiceException;
import pe.business.app.calls.infrastructure.api.dto.CallHistoryRs;
import pe.business.app.calls.infrastructure.api.dto.CallRq;
import pe.business.app.calls.infrastructure.api.dto.CallRs;

import pe.business.app.calls.infrastructure.api.gateway.ApiRatesClient;
import pe.business.app.calls.infrastructure.api.mapper.CallHistoryMapper;
import pe.business.app.calls.infrastructure.repository.CallsRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class CallsServiceImpl implements CallsService {



    @Autowired
    SaveCallsUseCase saveCallsUseCase;

    @Autowired
    GetCallHistoryUseCase getCallHistoryUseCase;

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    @Override
    public Mono<CallRs> saveCall(CallRq callRq) {

      return   saveCallsUseCase.saveCall(callRq);

    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Page<CallHistoryRs>> getCallHistory(String fromDate, String toDate , PageRequest pageRequest) {

        LocalDateTime fromDateLocal = LocalDateTime.parse(fromDate,formatter);
        LocalDateTime toDateLocal = LocalDateTime.parse(toDate,formatter);

        return  getCallHistoryUseCase.getCallHistory(fromDateLocal,toDateLocal, pageRequest);
        //return  callsRepository.findAll().map(
        //        callHistory -> callHistoryMapper.toDto(callHistory));
    }





}
