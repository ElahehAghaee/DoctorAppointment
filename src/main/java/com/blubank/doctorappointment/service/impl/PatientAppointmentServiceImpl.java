package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.dao.AppointmentDao;
import com.blubank.doctorappointment.dao.repo.AppointmentRepository;
import com.blubank.doctorappointment.dao.repo.PatientRepository;
import com.blubank.doctorappointment.enums.ResponseStatus;
import com.blubank.doctorappointment.exception.BusinessException;
import com.blubank.doctorappointment.model.entity.Appointment;
import com.blubank.doctorappointment.model.entity.Patient;
import com.blubank.doctorappointment.model.request.TakeAppointmentsRequest;
import com.blubank.doctorappointment.model.request.AppointmentDto;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.PatientAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PatientAppointmentServiceImpl implements PatientAppointmentService {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    PatientRepository patientRepository;


    @Override
    public GeneralResponse viewOpenAppointments(AppointmentDto appointmentDto) {
        try {
            GeneralResponse generalResponse = new GeneralResponse();

            List<Appointment> appointments = new ArrayList<Appointment>();
            appointments = appointmentDao.findAllOpenAppointments(appointmentDto);

            generalResponse.setMessage(appointments);
            generalResponse.setError(false);
            generalResponse.setResult_number(Long.valueOf(appointments.size()));

            return generalResponse;

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public GeneralResponse takeAppointment(TakeAppointmentsRequest takeAppointmentsRequest) {
        try {
            GeneralResponse generalResponse = new GeneralResponse();

            Appointment appointment = appointmentRepository.findByIdAndPatientId(takeAppointmentsRequest.getAppointmentId(),null);

            if (appointment!=null) {
                throw new BusinessException(ResponseStatus.APPOINTMENT_ALREADY_TAKEN_OR_DELETED);
            }

            Patient existPatient = patientRepository.findFirstByPhoneNumber(takeAppointmentsRequest.getPhoneNumber());
            if (existPatient == null) {
                Patient newPatient = new Patient();
                newPatient.setName(takeAppointmentsRequest.getPatientName());
                newPatient.setPhoneNumber(takeAppointmentsRequest.getPhoneNumber());
                patientRepository.save(newPatient);
                appointment.setPatient(newPatient);
            } else
                appointment.setPatient(existPatient);

            appointmentRepository.save(appointment);

            generalResponse.setMessage(appointment);
            generalResponse.setError(false);
            generalResponse.setResult_number(1L);
            return generalResponse;

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public GeneralResponse viewOwnAppointments(AppointmentDto appointmentDto) {
        try{
            GeneralResponse generalResponse=new GeneralResponse();

            List<Appointment> appointments=new ArrayList<Appointment>();
            appointments= appointmentDao.findAllOpenAppointments(appointmentDto);

            generalResponse.setMessage(appointments);
            generalResponse.setError(false);
            generalResponse.setResult_number(Long.valueOf(appointments.size()));

            return generalResponse;

        }catch(Exception ex){
            throw ex;
        }
    }

}
