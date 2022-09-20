package com.blubank.doctorappointment.dao.repo;


import com.blubank.doctorappointment.model.entity.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

    Doctor findFirstByName(String name);



}
