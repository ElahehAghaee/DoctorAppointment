package com.blubank.doctorappointment.controller;

import com.blubank.doctorappointment.model.entity.Doctor;
import com.blubank.doctorappointment.model.request.AddOpenTimesRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.DoctorAppointmentService;
import com.blubank.doctorappointment.service.DoctorService;
import com.blubank.doctorappointment.utils.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @Autowired
    DoctorAppointmentService doctorAppointmentService;

    //Crud services for Doctor Entity

    @PostMapping()
    ResponseEntity<GeneralResponse> create(@Valid @RequestBody Doctor doctor, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse GeneralResponse = doctorService.create(doctor);
        return new ResponseEntity<>(GeneralResponse, HttpStatus.OK);
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<GeneralResponse> update(@Valid @RequestBody Doctor doctor, @PathVariable Integer doctorId, Errors errors)
            throws Exception{
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = doctorService.update(doctor,doctorId);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> delete(@Valid  @PathVariable Integer id)
            throws Exception{
        GeneralResponse GeneralResponse = doctorService.delete(id);
        return new ResponseEntity<>(GeneralResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<GeneralResponse> list()
            throws Exception{
        GeneralResponse GeneralResponse = doctorService.list();
        return new ResponseEntity<>(GeneralResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> retrieve( @Valid  @PathVariable Integer id)
            throws Exception{
        GeneralResponse GeneralResponse = doctorService.retrieve(id);
        return new ResponseEntity<>(GeneralResponse, HttpStatus.OK);
    }

   //Doctor appointment services

    @PostMapping("/appointment/addOpenTimes")
    ResponseEntity<GeneralResponse> addOpenTimes(@Valid @RequestBody AddOpenTimesRequest addOpenTimesRequest, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = doctorAppointmentService.addOpenTimes(addOpenTimesRequest);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @PostMapping("/appointment/viewAppointments")
    ResponseEntity<GeneralResponse> viewAppointments(@Valid @RequestBody ViewAppointmentsRequest viewAppointmentsRequest, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = doctorAppointmentService.viewAppointments(viewAppointmentsRequest);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @DeleteMapping("/appointment/deleteOpenAppointment/{appointmentId}")
    public ResponseEntity<GeneralResponse> delete( @Valid @PathVariable Integer appointmentId,Errors errors)
            throws Exception{
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = doctorAppointmentService.deleteOpenAppointment(appointmentId);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }


}
