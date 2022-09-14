package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.model.request.AddOpenTimesRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.DoctorAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class DoctorAppointmentServiceImpl implements DoctorAppointmentService {
    private  Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public GeneralResponse addOpenTimes(AddOpenTimesRequest addOpenTimesRequest) {
        return null;
    }

    @Override
    public GeneralResponse viewAppointments(ViewAppointmentsRequest viewAppointmentsRequest) {
        return null;
    }

    @Override
    public GeneralResponse deleteOpenAppointment(Integer doctorId) {
        return null;
    }

}
