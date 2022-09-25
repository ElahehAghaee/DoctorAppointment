package com.blubank.doctorappointment.controller;

import com.blubank.doctorappointment.model.entity.Doctor;
import com.blubank.doctorappointment.model.request.AppointmentDto;
import com.blubank.doctorappointment.model.request.TakeAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.DoctorAppointmentService;
import com.blubank.doctorappointment.service.DoctorService;
import com.blubank.doctorappointment.service.PatientAppointmentService;
import com.blubank.doctorappointment.utils.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {


    @Autowired
    DoctorAppointmentService doctorAppointmentService;

    @Autowired
    PatientAppointmentService patientAppointmentService;

    //Doctor appointment services

    @PostMapping("/doctor/addOpenTimes")
    ResponseEntity<GeneralResponse> addOpenTimes(@Valid @RequestBody AppointmentDto appointmentDto, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = doctorAppointmentService.addOpenTimes(appointmentDto);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/doctor/viewAppointments")
    ResponseEntity<GeneralResponse> viewAppointments(@Valid @RequestBody AppointmentDto appointmentDto, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = doctorAppointmentService.viewAppointments(appointmentDto);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> delete( @PathVariable Integer id)
            throws Exception{
        //functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = doctorAppointmentService.deleteAppointment(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    //Patient appointment services

    @GetMapping("/patient/viewOpenAppointments")
    ResponseEntity<GeneralResponse> viewOpenAppointments(@Valid @RequestBody AppointmentDto appointmentDto, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = patientAppointmentService.viewOpenAppointments(appointmentDto);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @PostMapping("/patient/takeAppointment")
    ResponseEntity<GeneralResponse> takeAppointment(@Valid @RequestBody TakeAppointmentsRequest takeAppointmentsRequest, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = patientAppointmentService.takeAppointment(takeAppointmentsRequest);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/patient/viewOwnAppointments")
    ResponseEntity<GeneralResponse> viewOwnAppointments(@Valid @RequestBody AppointmentDto appointmentDto, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = patientAppointmentService.viewOwnAppointments(appointmentDto);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }







}
