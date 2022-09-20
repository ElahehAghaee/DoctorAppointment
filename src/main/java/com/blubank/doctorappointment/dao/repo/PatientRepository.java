package com.blubank.doctorappointment.dao.repo;

import com.blubank.doctorappointment.model.entity.Patient;
import org.springframework.data.repository.CrudRepository;


public interface PatientRepository extends CrudRepository<Patient, Integer> {

    Patient findFirstByPhoneNumber(String phoneNumber);


}
