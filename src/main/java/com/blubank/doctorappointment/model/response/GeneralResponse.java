package com.blubank.doctorappointment.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralResponse {
    private Boolean error;
    private Object message;
    private Long result_number;
    private Long sync;

    @Override
    public String toString() {
        return "GeneralResponse{" +
                "error=" + error +
                ", message=" + message +
                ", result_number=" + result_number +
                '}';
    }

    public GeneralResponse(Boolean error, Object message, Long result_number){
        this.error=error;
        this.message=message;
        this.result_number=result_number;
    }

}
