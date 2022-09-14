package com.blubank.doctorappointment.controller;


import com.blubank.doctorappointment.model.request.TakeAppointmentsRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.PatientAppointmentService;
import com.blubank.doctorappointment.utils.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/patient/appointment")
public class PatientController {


    @Autowired
    PatientAppointmentService patientAppointmentService;

    @PostMapping("/viewOpenAppointments")
    ResponseEntity<GeneralResponse> viewOpenAppointments(@Valid @RequestBody ViewAppointmentsRequest viewAppointmentsRequest, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = patientAppointmentService.viewOpenAppointments(viewAppointmentsRequest);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @PostMapping("/takeAppointment")
    ResponseEntity<GeneralResponse> takeAppointment(@Valid @RequestBody TakeAppointmentsRequest takeAppointmentsRequest, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = patientAppointmentService.takeAppointment(takeAppointmentsRequest);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @PostMapping("/viewOwnAppointments")
    ResponseEntity<GeneralResponse> viewOwnAppointments(@Valid @RequestBody ViewAppointmentsRequest viewAppointmentsRequest, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = patientAppointmentService.viewOwnAppointments(viewAppointmentsRequest);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }



















}
