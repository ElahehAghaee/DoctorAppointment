package com.blubank.doctorappointment.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TakeAppointmentsRequest {

    private Integer appointmentId;

    @NotNull
    @NotEmpty
    private String patientName;

    @NotNull
    @NotEmpty
    private String phoneNumber;
}
