package com.blubank.doctorappointment.enums;

import java.util.Arrays;
import java.util.Objects;

public enum ResponseStatus {
    SUCCESS(200L, "SUCCESS"),
    INTERNAL_ERROR(2950010000L, "Internal error!"),
    BAD_PARAMETERS(2940010001L, "Parameters validation error!please check parameters name, value and nullability!"),
    BAD_REQUEST(2940010002L,"Bad Request"),
    METHOD_ARGUMENT_NOT_VALID(2940010003L, "Method Argument  not valid!"),
    END_DATE_IS_SOONER_THAN_START_DATE(2940010004L, "End date is sooner than start date!"),
    DURATION_IS_LESS_THAN_30_MINUTES(2940010005L, "Duration is less than 30 minutes!"),
    DOCTOR_NAME_ALREADY_EXIST(2940010006L, "Doctor name Already exist"),
    PATIENT_PHONE_NUMBER_ALREADY_EXIST(2940010007L, "Patient phone number Already exist!"),
    URL_NOT_FOUND(2940410001L, "Url not found!"),
    No_OPEN_APPOINTMENT(2940410002L, "There is no open appointment!"),
    DOCTOR_NOT_FOUND(2940410003L, "Doctor not found!"),
    PATIENT_NOT_FOUND(2940410004L, "Patient not found!"),
    NOT_ACCEPTABLE(2940610000L,"Not Acceptable Header!"),
    APPOINTMENT_IS_TAKEN_BY_PATIENT(2940610001L,"This appointment is taken by a patient!");

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
