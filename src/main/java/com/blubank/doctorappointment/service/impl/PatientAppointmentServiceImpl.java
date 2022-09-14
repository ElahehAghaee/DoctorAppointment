package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.model.request.TakeAppointmentsRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.PatientAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class PatientAppointmentServiceImpl implements PatientAppointmentService {
    private  Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public GeneralResponse viewOpenAppointments(ViewAppointmentsRequest viewAppointmentsRequest) {
        return null;
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
