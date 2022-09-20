package com.blubank.doctorappointment.service;


import com.blubank.doctorappointment.model.entity.Doctor;
import com.blubank.doctorappointment.model.entity.Patient;
import com.blubank.doctorappointment.model.request.AddOpenTimesRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;

public interface DoctorService {

    GeneralResponse create(Doctor doctor) throws Exception;
    GeneralResponse update(Doctor doctor, Integer id);
    GeneralResponse delete(Integer id);
    GeneralResponse list();
    GeneralResponse retrieve(Integer id) throws Exception;
}
