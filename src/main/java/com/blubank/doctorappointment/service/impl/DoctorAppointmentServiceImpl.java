package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.dao.AppointmentDao;
import com.blubank.doctorappointment.dao.repo.AppointmentRepository;
import com.blubank.doctorappointment.enums.ResponseStatus;
import com.blubank.doctorappointment.exception.BusinessException;
import com.blubank.doctorappointment.model.entity.Appointment;
import com.blubank.doctorappointment.model.request.AddOpenTimesRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.DoctorAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DoctorAppointmentServiceImpl implements DoctorAppointmentService {
    private  Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentDao appointmentDao;


    @Override
    public GeneralResponse addOpenTimes(AddOpenTimesRequest addOpenTimesRequest) {
        try{
            GeneralResponse generalResponse=new GeneralResponse();
            if(addOpenTimesRequest!=null && addOpenTimesRequest.getStartTime()>addOpenTimesRequest.getEndTime())
                throw new BusinessException(ResponseStatus.END_DATE_IS_SOONER_THAN_START_DATE);

        Long duration= addOpenTimesRequest.getEndTime()-addOpenTimesRequest.getStartTime();
        if(duration>=1800000){
            Long openTimesCount= duration/(1800000);
            Long periodStartTime=addOpenTimesRequest.getStartTime();

            List<Appointment> openTimesAppointment=new ArrayList<Appointment>();

            for (int i = 0; i < openTimesCount; i++) {
                Appointment appointment=new Appointment();

                appointment.setStartTime(periodStartTime);
                appointment.setEndTime(addOpenTimesRequest.getStartTime()+1800000);
                appointment.setDoctorId(addOpenTimesRequest.getDoctorId());
                appointment.setDate(addOpenTimesRequest.getDate());

                periodStartTime=addOpenTimesRequest.getStartTime()+1800000;

                openTimesAppointment.add(appointment);
                appointmentRepository.save(appointment);
            }
            generalResponse.setMessage(openTimesAppointment);
            generalResponse.setError(false);
            generalResponse.setResult_number(openTimesCount);

        }else{
            throw new BusinessException(ResponseStatus.DURATION_IS_LESS_THAN_30_MINUTES);
        }

        return generalResponse;

        }catch(Exception ex){
            throw ex;
        }

    }

    @Override
    public GeneralResponse viewAppointments(ViewAppointmentsRequest viewAppointmentsRequest) {
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
    public GeneralResponse deleteOpenAppointment(Integer appointmentId) {
        try {
            GeneralResponse generalResponse = new GeneralResponse();

            Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);

            if (optionalAppointment.isPresent() != true)
                throw new BusinessException(ResponseStatus.No_OPEN_APPOINTMENT);


            if (optionalAppointment.get().getPatientId() != null)
                throw new BusinessException(ResponseStatus.APPOINTMENT_IS_TAKEN_BY_PATIENT);


            //Concurrency Check.......................


            appointmentRepository.delete(optionalAppointment.get());

            generalResponse.setMessage("Successfully deleted.");
            generalResponse.setError(false);
            generalResponse.setResult_number(1L);

            return generalResponse;

        } catch (Exception ex) {
            throw ex;
        }
    }
}
