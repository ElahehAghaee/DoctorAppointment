package com.blubank.doctorappointment.controller;

import com.blubank.doctorappointment.model.entity.Patient;
import com.blubank.doctorappointment.model.request.TakeAppointmentsRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.PatientAppointmentService;
import com.blubank.doctorappointment.service.PatientService;
import com.blubank.doctorappointment.utils.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    PatientAppointmentService patientAppointmentService;


    //Crud services for Patient Entity

    @PostMapping()
    ResponseEntity<GeneralResponse> create(@Valid @RequestBody Patient patient, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse GeneralResponse = patientService.create(patient);
        return new ResponseEntity<>(GeneralResponse, HttpStatus.OK);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<GeneralResponse> update(@Valid @RequestBody Patient patient, @PathVariable Integer patientId, Errors errors)
            throws Exception{
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = patientService.update(patient,patientId);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> delete(@Valid  @PathVariable Integer id)
            throws Exception{
        GeneralResponse GeneralResponse = patientService.delete(id);
        return new ResponseEntity<>(GeneralResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<GeneralResponse> list()
            throws Exception{
        GeneralResponse GeneralResponse = patientService.list();
        return new ResponseEntity<>(GeneralResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> retrieve( @Valid  @PathVariable Integer id)
            throws Exception{
        GeneralResponse GeneralResponse = patientService.retrieve(id);
        return new ResponseEntity<>(GeneralResponse, HttpStatus.OK);
    }

    //Patient appointment sevices

    @PostMapping("/appointment/viewOpenAppointments")
    ResponseEntity<GeneralResponse> viewOpenAppointments(@Valid @RequestBody ViewAppointmentsRequest viewAppointmentsRequest, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = patientAppointmentService.viewOpenAppointments(viewAppointmentsRequest);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @PostMapping("/appointment/takeAppointment")
    ResponseEntity<GeneralResponse> takeAppointment(@Valid @RequestBody TakeAppointmentsRequest takeAppointmentsRequest, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = patientAppointmentService.takeAppointment(takeAppointmentsRequest);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @PostMapping("/appointment/viewOwnAppointments")
    ResponseEntity<GeneralResponse> viewOwnAppointments(@Valid @RequestBody ViewAppointmentsRequest viewAppointmentsRequest, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = patientAppointmentService.viewOwnAppointments(viewAppointmentsRequest);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }


}
