package com.blubank.doctorappointment.service;


import com.blubank.doctorappointment.model.entity.Patient;
import com.blubank.doctorappointment.model.response.GeneralResponse;

public interface PatientService {

    GeneralResponse create(Patient patient) throws Exception;
    GeneralResponse update(Patient patient, Integer id);
    GeneralResponse delete(Integer id);
    GeneralResponse list();
    GeneralResponse retrieve(Integer id) throws Exception;


}
