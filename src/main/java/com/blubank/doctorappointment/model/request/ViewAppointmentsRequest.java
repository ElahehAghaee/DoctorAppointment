package com.blubank.doctorappointment.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewAppointmentsRequest {

    private Integer doctorId;
    private Boolean openStaus;
    private String phoneNumber;
    private Long fromDate;
    private Long toDate;
}
