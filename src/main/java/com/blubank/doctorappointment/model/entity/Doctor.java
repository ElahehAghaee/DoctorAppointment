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
@Table(name = "doctor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @JsonIgnore
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    @Column(name = "name")
    private String name;


}