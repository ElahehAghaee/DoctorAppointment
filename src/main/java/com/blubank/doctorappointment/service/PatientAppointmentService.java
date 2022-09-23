package com.blubank.doctorappointment.service;


import com.blubank.doctorappointment.model.request.TakeAppointmentsRequest;
import com.blubank.doctorappointment.model.request.AppointmentDto;
import com.blubank.doctorappointment.model.response.GeneralResponse;

public interface PatientAppointmentService {

    GeneralResponse viewOpenAppointments(AppointmentDto appointmentDto);
    GeneralResponse takeAppointment(TakeAppointmentsRequest takeAppointmentsRequest);
    GeneralResponse viewOwnAppointments(AppointmentDto appointmentDto);

}
