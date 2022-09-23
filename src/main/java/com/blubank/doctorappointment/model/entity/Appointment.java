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

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;


    @NotNull
    @Column(name = "start_date_time")
    private Long startDateTime;


    @NotNull
    @Column(name = "end_date_time")
    private Long endDateTime;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id")
    private Patient patient;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctor_id")
    @NotNull
    private Doctor doctor;





}