package com.blubank.doctorappointment.service;


import com.blubank.doctorappointment.model.entity.Doctor;
import com.blubank.doctorappointment.model.entity.Patient;
import com.blubank.doctorappointment.model.request.AddOpenTimesRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;

public interface PatientService {

    GeneralResponse create(Patient patient) throws Exception;
    GeneralResponse update(Patient patient, Integer id);
    GeneralResponse delete(Integer id);
    GeneralResponse list();
    GeneralResponse retrieve(Integer id) throws Exception;


}
