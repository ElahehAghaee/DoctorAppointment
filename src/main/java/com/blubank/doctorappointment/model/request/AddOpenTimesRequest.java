package com.blubank.doctorappointment.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddOpenTimesRequest {

    private Integer doctorId;
    private Long startTime;
    private Long endTime;
    private Long date;
}
