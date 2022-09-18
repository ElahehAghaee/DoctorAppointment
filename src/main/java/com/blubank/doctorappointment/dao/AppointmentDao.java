package com.blubank.doctorappointment.dao;


import com.blubank.doctorappointment.model.entity.Appointment;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;

import java.util.List;

public interface AppointmentDao {

    public List<Appointment> findAllOpenAppointments(ViewAppointmentsRequest viewAppointmentsRequest);


}
