package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.dao.AppointmentDao;
import com.blubank.doctorappointment.dao.repo.AppointmentRepository;
import com.blubank.doctorappointment.dao.repo.DoctorRepository;
import com.blubank.doctorappointment.enums.ResponseStatus;
import com.blubank.doctorappointment.exception.BusinessException;
import com.blubank.doctorappointment.model.entity.Appointment;
import com.blubank.doctorappointment.model.entity.Doctor;
import com.blubank.doctorappointment.model.request.AppointmentDto;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.DoctorAppointmentService;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DoctorAppointmentServiceImpl implements DoctorAppointmentService {
    private  Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    DoctorRepository doctorRepository;

    @Value("${time.to.break.down}")
    private Long timeToBreakDown;

    @Override
    public GeneralResponse addOpenTimes(AppointmentDto appointmentDto) {
        try{
            GeneralResponse generalResponse=new GeneralResponse();
            if(appointmentDto!=null && appointmentDto.getStartDateTime()>appointmentDto.getEndDateTime())
                throw new BusinessException(ResponseStatus.END_DATE_IS_SOONER_THAN_START_DATE);

        Optional<Doctor> doctor=doctorRepository.findById(appointmentDto.getDoctorId());
        if(!doctor.isPresent())
            throw new BusinessException(ResponseStatus.DOCTOR_NOT_FOUND);

        //30 minutes is equal to 1800000 milliseconds
        Long duration= appointmentDto.getEndDateTime()-appointmentDto.getStartDateTime();
        if(duration<timeToBreakDown)
            throw new BusinessException(ResponseStatus.DURATION_IS_LESS_THAN_30_MINUTES);

        if(checkTimeOvelap(appointmentDto))
            throw new BusinessException(ResponseStatus.TIMES_HAVE_OVERLAP);

            Long periodStartTime=appointmentDto.getStartDateTime();
            List<Appointment> openTimesAppointment=new ArrayList<Appointment>();
            while(periodStartTime<appointmentDto.getEndDateTime() && periodStartTime+timeToBreakDown<=appointmentDto.getEndDateTime()){
                Appointment appointment=new Appointment();
                appointment.setStartDateTime(periodStartTime);
                appointment.setEndDateTime(periodStartTime+timeToBreakDown);
                appointment.setDoctor(doctor.get());
                appointment.getDoctor().setId(appointmentDto.getDoctorId());

                periodStartTime=periodStartTime+timeToBreakDown;

                openTimesAppointment.add(appointment);
                appointmentRepository.save(appointment);
            }

            generalResponse.setMessage(openTimesAppointment);
            generalResponse.setError(false);
            generalResponse.setResult_number(Long.valueOf(openTimesAppointment.size()));


        return generalResponse;

        }catch(Exception ex){
            throw ex;
        }

    }

    @Override
    public GeneralResponse viewAppointments(AppointmentDto appointmentDto) {
        try{
            GeneralResponse generalResponse=new GeneralResponse();

            List<Appointment> appointments=new ArrayList<Appointment>();
            appointments= appointmentDao.findAllOpenAppointments(appointmentDto);

           if(appointments!=null && appointments.size()>0)
           {
             appointments.stream()
            .filter(appointment -> appointment.getPatient()!=null)
            .peek(appointment -> appointment.setPatientName(appointment.getPatient().getName()))
            .peek(appointment -> appointment.setPatientPhoneNumber(appointment.getPatient().getPhoneNumber()))
            .collect(Collectors.toList());
             }

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


            if (optionalAppointment.get().getPatient()!= null)
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

    private Boolean checkTimeOvelap(AppointmentDto appointmentDto){
        Boolean hasTimeOverLap=false;
        List<Appointment> appointments=appointmentDao.FindTimeOverLaps(appointmentDto);

        if(appointments!=null && appointments.size()>0)
        hasTimeOverLap=true;

       return hasTimeOverLap;
    }





}
