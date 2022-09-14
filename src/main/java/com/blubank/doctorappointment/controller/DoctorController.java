package com.blubank.doctorappointment.controller;


import com.blubank.doctorappointment.model.request.AddOpenTimesRequest;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import com.blubank.doctorappointment.service.DoctorAppointmentService;
import com.blubank.doctorappointment.utils.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/doctor/appointment")
public class DoctorController {


    @Autowired
    DoctorAppointmentService doctorAppointmentService;

    @PostMapping("/addOpenTimes")
    ResponseEntity<GeneralResponse> addOpenTimes(@Valid @RequestBody AddOpenTimesRequest addOpenTimesRequest, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = doctorAppointmentService.addOpenTimes(addOpenTimesRequest);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @PostMapping("/viewAppointments")
    ResponseEntity<GeneralResponse> viewAppointments(@Valid @RequestBody ViewAppointmentsRequest viewAppointmentsRequest, Errors errors) throws Exception {
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = doctorAppointmentService.viewAppointments(viewAppointmentsRequest);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @DeleteMapping("deleteOpenAppointment/{doctorId}")
    public ResponseEntity<GeneralResponse> delete( @Valid @PathVariable Integer doctorId,Errors errors)
            throws Exception{
        functions.handleParameterValidationException(errors);
        GeneralResponse generalResponse = doctorAppointmentService.deleteOpenAppointment(doctorId);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }













}
