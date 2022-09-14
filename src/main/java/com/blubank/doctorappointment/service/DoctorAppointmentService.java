package com.blubank.doctorappointment.service;


import com.blubank.doctorappointment.model.request.AddOpenTimesRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;

public interface DoctorAppointmentService {

    GeneralResponse addOpenTimes(AddOpenTimesRequest addOpenTimesRequest);
    GeneralResponse viewAppointments(ViewAppointmentsRequest viewAppointmentsRequest);
    GeneralResponse deleteOpenAppointment(Integer doctorId);


}
