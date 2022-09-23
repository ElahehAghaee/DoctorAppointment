package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.dao.repo.PatientRepository;
import com.blubank.doctorappointment.enums.ResponseStatus;
import com.blubank.doctorappointment.exception.BusinessException;
import com.blubank.doctorappointment.model.entity.Doctor;
import com.blubank.doctorappointment.model.entity.Patient;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;


    @Override
    public GeneralResponse create(Patient patient) throws Exception {
        GeneralResponse generalResponse = new GeneralResponse();
        Patient checkExistanceBefore=patientRepository.findFirstByPhoneNumber(patient.getPhoneNumber());
        if(checkExistanceBefore!=null)
            throw new BusinessException(ResponseStatus.PATIENT_PHONE_NUMBER_ALREADY_EXIST);

        patientRepository.save(patient);
        generalResponse.setMessage(patient);
        generalResponse.setError(false);
        generalResponse.setResult_number(1L);
        return generalResponse;
    }

    @Override
    public GeneralResponse update(Patient patient, Integer patientId) {
        GeneralResponse generalResponse = new GeneralResponse();
        Optional<Patient> optionalPatient=patientRepository.findById(patientId);

        if(!optionalPatient.isPresent()){
            throw new BusinessException(ResponseStatus.PATIENT_NOT_FOUND);
        }
        Patient newPatient=optionalPatient.get();
        newPatient.setName(patient.getName());
        newPatient.setPhoneNumber(patient.getPhoneNumber());
        patientRepository.save(newPatient);

        generalResponse.setMessage(newPatient);
        generalResponse.setError(false);
        generalResponse.setResult_number(1L);
        return generalResponse;
    }

    @Override
    public GeneralResponse delete(Integer id) {
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            Optional<Patient> optionalPatient= patientRepository.findById(id);
            if(optionalPatient.isPresent()){
                Patient patient=optionalPatient.get();
                patientRepository.delete(patient);

                generalResponse.setMessage("Successfully deleted.");
                generalResponse.setError(false);
                generalResponse.setResult_number(1L);
                return generalResponse;
            }else
            {
                throw new BusinessException(ResponseStatus.PATIENT_NOT_FOUND);
            }
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public GeneralResponse list() {
        GeneralResponse generalResponse = new GeneralResponse();
        List<Patient> patients = new ArrayList<Patient>();
        try{
            patients = (List<Patient>) patientRepository.findAll();
            generalResponse.setMessage(patients);
            generalResponse.setError(false);
            generalResponse.setResult_number(1L);
            return generalResponse;
        } catch (Exception e){
            throw e;
        }
    }


    @Override
    public GeneralResponse retrieve(Integer id) throws Exception {
        GeneralResponse generalResponse = new GeneralResponse();

        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            generalResponse.setMessage(patient.get());
            generalResponse.setError(false);
            generalResponse.setResult_number(1L);

            return generalResponse;
        } else {
            throw new BusinessException(ResponseStatus.PATIENT_NOT_FOUND);
        }
    }


}
