package pe.business.app.calls.infrastructure.api.mapper;

import com.google.gson.Gson;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pe.business.app.calls.infrastructure.api.dto.CallHistoryRs;
import pe.business.app.calls.infrastructure.api.dto.CallRq;
import pe.business.app.calls.infrastructure.api.dto.CallRs;
import pe.business.app.calls.infrastructure.repository.entity.CallHistory;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Mapper(componentModel = "spring")
public  interface CallHistoryMapper {

    @Named("toDto")
    @Mapping(target = "datetime", expression = "java(getDateViaInstant(callHistory.getDatetime()))")
    CallHistoryRs toDto(CallHistory callHistory);

    @Named("toEntity")
    @Mapping(target = "datetime", source = "date")
    @Mapping(target = "endpoint", expression = "java(getEndPoint())")
    @Mapping(target = "request", expression = "java(getRequest(callRq))")
    @Mapping(target = "result", expression = "java(getResult(callRs))")
    CallHistory toEntity(LocalDateTime date, CallRq callRq , CallRs callRs);

    @Named("toEntityError")
    @Mapping(target = "datetime", source = "date")
    @Mapping(target = "endpoint", expression = "java(getEndPoint())")
    @Mapping(target = "request", expression = "java(getRequest(callRq))")
    @Mapping(target = "result", expression = "java(getResultError(message))")
    CallHistory toEntityError(LocalDateTime date, CallRq callRq , String message);


    @IterableMapping(qualifiedByName = "toDto")
    List<CallHistoryRs> toDtos(List<CallHistory> callHistoryList);

    default String getEndPoint() {
        return "/grb/calls";
    }

    default String getRequest(CallRq callRq) {
        return new Gson().toJson(callRq);
    }


    default String getDateViaInstant(LocalDateTime dateToConvert) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant()));
    }

    default String getResult(CallRs callRs) {
        return new Gson().toJson(callRs);
    }

    default String getResultError(String message) {
        return new Gson().toJson(message);
    }
}