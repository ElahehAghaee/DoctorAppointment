package com.blubank.doctorappointment.service;


import com.blubank.doctorappointment.model.request.AppointmentDto;
import com.blubank.doctorappointment.model.response.GeneralResponse;

public interface DoctorAppointmentService {

    GeneralResponse addOpenTimes(AppointmentDto appointmentDto);
    GeneralResponse viewAppointments(AppointmentDto appointmentDto);
    GeneralResponse deleteOpenAppointment(Integer appointmentId);


}
