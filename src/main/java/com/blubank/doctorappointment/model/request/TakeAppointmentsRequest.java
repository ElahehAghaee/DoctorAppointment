package com.blubank.doctorappointment.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TakeAppointmentsRequest {

    private String patientName;
    private String phoneNumber;
}
