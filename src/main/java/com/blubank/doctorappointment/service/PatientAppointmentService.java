package com.blubank.doctorappointment.service;


import com.blubank.doctorappointment.model.request.AddOpenTimesRequest;
import com.blubank.doctorappointment.model.request.TakeAppointmentsRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;

public interface PatientAppointmentService {

    GeneralResponse viewOpenAppointments(ViewAppointmentsRequest viewAppointmentsRequest);
    GeneralResponse takeAppointment(TakeAppointmentsRequest takeAppointmentsRequest);
    GeneralResponse viewOwnAppointments(ViewAppointmentsRequest viewAppointmentsRequest);

}
