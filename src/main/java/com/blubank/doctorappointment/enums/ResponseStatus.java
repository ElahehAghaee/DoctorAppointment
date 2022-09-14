package com.blubank.doctorappointment.enums;

import java.util.Arrays;
import java.util.Objects;

public enum ResponseStatus {
    SUCCESS(200L, "SUCCESS"),
    INTERNAL_ERROR(2950010000L, "Internal error!"),
    BAD_PARAMETERS(2940010001L, "Parameters validation error!please check parameters name, value and nullability!"),
    BAD_REQUEST(2940010002L,"Bad Request"),
    METHOD_ARGUMENT_NOT_VALID(2940010003L, "Method Argument  not valid!"),
    URL_NOT_FOUND(2940410001L, "Url not found!"),
    REQUEST_DATA_NOT_FOUND(2940410002L, "RequestData not found!"),
    NOT_ACCEPTABLE(2940610000L,"Not Acceptable Header!");



    private Long code;
    private String description;

    ResponseStatus() {
    }

    ResponseStatus(Long code, String description) {
        this.code = code;
        this.description = description;
    }

    public Long getCode() {
        return code;
    }

    public static String getCodeAsString(ResponseStatus ts) {
        return String.valueOf(ts.code);
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TransactionState{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }

    public static ResponseStatus getState(int code) {
        return Arrays.stream(ResponseStatus.values())
                .filter(o -> o.code == code)
                .findFirst()
                .orElse(ResponseStatus.INTERNAL_ERROR);
    }

    public static ResponseStatus extractTransaction(String param) {
        return Arrays.stream(ResponseStatus.values())
                .filter(o -> Objects.equals(o.name().replace("_", "").toUpperCase(), param.toUpperCase()))
                .findFirst()
                .orElse(ResponseStatus.INTERNAL_ERROR);
    }
}
