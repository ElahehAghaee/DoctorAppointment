package com.blubank.doctorappointment.dao;


import com.blubank.doctorappointment.model.entity.Appointment;
import com.blubank.doctorappointment.model.request.AppointmentDto;

import java.util.List;

public interface AppointmentDao {

     List<Appointment> findAllOpenAppointments(AppointmentDto appointmentDto);
     List<Appointment> hasTimeOverLap(AppointmentDto appointmentDto);


}
