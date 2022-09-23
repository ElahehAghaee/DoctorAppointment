package com.blubank.doctorappointment.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Integer doctorId;
    private Boolean openStaus;
    private String phoneNumber;
    private Long startDateTime;
    private Long endDateTime;
}
