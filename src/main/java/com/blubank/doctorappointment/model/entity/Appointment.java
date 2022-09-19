package com.blubank.doctorappointment.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @JsonIgnore
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

//    @NotNull
//    @NotEmpty
//    @Column(name = "doctor_id")
//    private Integer doctorId;


    @NotNull
    @NotEmpty
    @Column(name = "start_time")
    private Long startTime;


    @NotNull
    @NotEmpty
    @Column(name = "end_time")
    private Long endTime;

    @NotNull
    @NotEmpty
    @Column(name = "date")
    private Long date;

//    @Column(name = "patient_id")
//    private Integer patientId;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id")
    private Patient patient;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctor_id")
    private Doctor doctor;





}