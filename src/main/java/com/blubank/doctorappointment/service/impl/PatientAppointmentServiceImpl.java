package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.dao.AppointmentDao;
import com.blubank.doctorappointment.dao.repo.AppointmentRepository;
import com.blubank.doctorappointment.model.entity.Appointment;
import com.blubank.doctorappointment.model.request.TakeAppointmentsRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.PatientAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PatientAppointmentServiceImpl implements PatientAppointmentService {
    private  Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentDao appointmentDao;


    @Override
    public GeneralResponse viewOpenAppointments(ViewAppointmentsRequest viewAppointmentsRequest) {
        try{
            GeneralResponse generalResponse=new GeneralResponse();

            List<Appointment> appointments=new ArrayList<Appointment>();
            appointments= appointmentDao.findAllOpenAppointments(viewAppointmentsRequest);

            generalResponse.setMessage(appointments);
            generalResponse.setError(false);
            generalResponse.setResult_number(Long.valueOf(appointments.size()));

            return generalResponse;

        }catch(Exception ex){
            throw ex;
        }
    }

    @Override
    public GeneralResponse takeAppointment(TakeAppointmentsRequest takeAppointmentsRequest) {
        return null;
    }

    @Override
    public GeneralResponse viewOwnAppointments(ViewAppointmentsRequest viewAppointmentsRequest) {
        return null;
    }
}
