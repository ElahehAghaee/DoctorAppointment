package com.blubank.doctorappointment.dao.repo;


import com.blubank.doctorappointment.model.entity.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

    List<Appointment> findAllByDoctorIdAndPatientId(Integer doctorId, Integer patientId);
    Appointment findByIdAndPatientId(Integer Id, Integer patientId);



}
