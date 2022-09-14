package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;


@Getter
@Setter
public class BusinessException extends RuntimeException implements Supplier<RuntimeException> {

    private Boolean error=true;
    private String message;
    private String result_number;


    public BusinessException() {
        super();
    }

    public BusinessException(String result_number,String message) {
       this.result_number=result_number;
       this.message=message;
    }

    public BusinessException(ResponseStatus responseStatus, String additional_message) {
        this.result_number=String.valueOf(responseStatus.getCode());
        this.message=responseStatus.getDescription()+" : "+additional_message;
    }

    public BusinessException(ResponseStatus responseStatus) {
        this.result_number=String.valueOf(responseStatus.getCode());
        this.message=responseStatus.getDescription();
    }


    @Override
    public String toString() {
        return "BusinessException{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", result_number='" + result_number + '\'' +
                '}';
    }

    @Override
    public RuntimeException get() {
        return null;
    }
}
