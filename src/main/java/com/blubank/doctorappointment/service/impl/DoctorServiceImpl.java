package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.dao.repo.AppointmentRepository;
import com.blubank.doctorappointment.dao.repo.DoctorRepository;
import com.blubank.doctorappointment.enums.ResponseStatus;
import com.blubank.doctorappointment.exception.BusinessException;
import com.blubank.doctorappointment.model.entity.Appointment;
import com.blubank.doctorappointment.model.entity.Doctor;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AppointmentRepository appointmentRepository;


    @Override
    public GeneralResponse create(Doctor doctor) throws Exception {
        GeneralResponse generalResponse = new GeneralResponse();
        Doctor checkExistanceBefore=doctorRepository.findFirstByName(doctor.getName());
        if(checkExistanceBefore!=null)
            throw new BusinessException(ResponseStatus.DOCTOR_NAME_ALREADY_EXIST);

        doctorRepository.save(doctor);
        generalResponse.setMessage(doctor);
        generalResponse.setError(false);
        generalResponse.setResult_number(1L);
        return generalResponse;
    }

    @Override
    public GeneralResponse update(Doctor doctor, Integer doctorId) {
        GeneralResponse generalResponse = new GeneralResponse();
        Optional<Doctor> optionalDoctor=doctorRepository.findById(doctorId);
        Doctor newDoctor=null;

        if(!optionalDoctor.isPresent()){
            throw new BusinessException(ResponseStatus.DOCTOR_NOT_FOUND);
        }
        newDoctor=optionalDoctor.get();
        newDoctor.setName(doctor.getName());
        doctorRepository.save(newDoctor);

        generalResponse.setMessage(newDoctor);
        generalResponse.setError(false);
        generalResponse.setResult_number(1L);
        return generalResponse;
    }

    @Override
    public GeneralResponse delete(Integer id) {
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
            if(optionalDoctor.isPresent()){
                Doctor doctor=optionalDoctor.get();

                List<Appointment> appointments=appointmentRepository.findAllByDoctorId(id);

                if(appointments!=null && appointments.size()>0)
                   throw new BusinessException(ResponseStatus.DOCTOR_WITH_APPOINTMENT_CANT_DELETE);

                doctorRepository.delete(doctor);

                generalResponse.setMessage("Successfully deleted.");
                generalResponse.setError(false);
                generalResponse.setResult_number(1L);
                return generalResponse;
            }else
            {
                throw new BusinessException(ResponseStatus.DOCTOR_NOT_FOUND);
            }
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public GeneralResponse list() {
        GeneralResponse generalResponse = new GeneralResponse();
        List<Doctor> doctors = new ArrayList<Doctor>();
        try{
            doctors = (List<Doctor>) doctorRepository.findAll();
            generalResponse.setMessage(doctors);
            generalResponse.setError(false);
            generalResponse.setResult_number(doctors!=null ? doctors.size(): 0L);
            return generalResponse;
        } catch (Exception e){
            throw e;
        }
    }


    @Override
    public GeneralResponse retrieve(Integer id) throws Exception {
        GeneralResponse generalResponse = new GeneralResponse();

        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isPresent()) {
            generalResponse.setMessage(doctor.get());
            generalResponse.setError(false);
            generalResponse.setResult_number(1L);

            return generalResponse;
        } else {
            throw new BusinessException(ResponseStatus.DOCTOR_NOT_FOUND);
        }
    }


}
