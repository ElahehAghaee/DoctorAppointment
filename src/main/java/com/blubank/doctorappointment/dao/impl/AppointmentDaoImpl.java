package com.blubank.doctorappointment.dao.impl;


import com.blubank.doctorappointment.dao.AppointmentDao;
import com.blubank.doctorappointment.model.entity.Appointment;
import com.blubank.doctorappointment.model.entity.Patient;
import com.blubank.doctorappointment.model.request.ViewAppointmentsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public class AppointmentDaoImpl implements AppointmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    private Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public List<Appointment> findAllOpenAppointments(ViewAppointmentsRequest viewAppointmentsRequest){
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery=criteriaBuilder.createQuery(Appointment.class);
        Root<Appointment> appointmentRoot = criteriaQuery.from(Appointment.class);
        Metamodel m = entityManager.getMetamodel();
        EntityType<Appointment> appointmentMetaModel = m.entity(Appointment.class);
        Join<Appointment, Patient> patientRoot= appointmentRoot.join("patient", JoinType.INNER);
        Set<Predicate> predicates = new HashSet<>(2);

        if(viewAppointmentsRequest.getDoctorId()!=null){
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("doctorId"),viewAppointmentsRequest.getDoctorId() ));
        }

       if (viewAppointmentsRequest.getOpenStaus()==true){
           predicates.add(criteriaBuilder.equal(appointmentRoot.get("patientId"),null ));
       }else if(viewAppointmentsRequest.getOpenStaus()==false){
           predicates.add(criteriaBuilder.notEqual(appointmentRoot.get("patientId"),null ));
       }

        if(viewAppointmentsRequest.getPhoneNumber()!=null){
            predicates.add(criteriaBuilder.equal(patientRoot.get("phoneNumber"),viewAppointmentsRequest.getPhoneNumber()));
        }


        if(viewAppointmentsRequest.getFromDate()!=null){
            predicates.add(criteriaBuilder.ge(appointmentRoot.get("date"), viewAppointmentsRequest.getFromDate()));
        }

        if(viewAppointmentsRequest.getToDate()!=null){
            predicates.add(criteriaBuilder.le(appointmentRoot.get("date"), viewAppointmentsRequest.getToDate()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

}
