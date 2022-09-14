package com.blubank.doctorappointment.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name = "patient")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotNull
    @NotEmpty
    @Column(name = "phone_number")
    private String phoneNumber;




}