package com.blubank.doctorappointment.dao.impl;


import com.blubank.doctorappointment.dao.AppointmentDao;
import com.blubank.doctorappointment.model.entity.Appointment;
import com.blubank.doctorappointment.model.entity.Doctor;
import com.blubank.doctorappointment.model.entity.Patient;
import com.blubank.doctorappointment.model.request.AppointmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class AppointmentDaoImpl implements AppointmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public List<Appointment> findAllOpenAppointments(AppointmentDto appointmentDto){
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery=criteriaBuilder.createQuery(Appointment.class);
        Root<Appointment> appointmentRoot = criteriaQuery.from(Appointment.class);
        Metamodel m = entityManager.getMetamodel();
        EntityType<Appointment> appointmentMetaModel = m.entity(Appointment.class);
        Join<Appointment, Patient> patientRoot= appointmentRoot.join("patient", JoinType.LEFT);
        Join<Appointment, Doctor> doctorRoot= appointmentRoot.join("doctor", JoinType.INNER);
        Set<Predicate> predicates = new HashSet<>(2);

        if(appointmentDto.getDoctorId()!=null){
            predicates.add(criteriaBuilder.equal(doctorRoot.get("id"), appointmentDto.getDoctorId() ));
        }

       if (appointmentDto.getOpenStaus()!=null && appointmentDto.getOpenStaus()==true){
           predicates.add(criteriaBuilder.isNull(patientRoot.get("id")));
       }else if(appointmentDto.getOpenStaus()!=null && appointmentDto.getOpenStaus()==false){
           predicates.add(criteriaBuilder.isNotNull(patientRoot.get("id") ));
       }

        if(appointmentDto.getPhoneNumber()!=null){
            predicates.add(criteriaBuilder.equal(patientRoot.get("phoneNumber"), appointmentDto.getPhoneNumber()));
        }

        if(appointmentDto.getStartDateTime()!=null){
            predicates.add(criteriaBuilder.ge(appointmentRoot.get("startDateTime"), appointmentDto.getStartDateTime()));
        }

        if(appointmentDto.getEndDateTime()!=null){
            predicates.add(criteriaBuilder.le(appointmentRoot.get("endDateTime"), appointmentDto.getEndDateTime()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    @Override
    public List<Appointment> FindTimeOverLaps(AppointmentDto appointmentDto){
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery=criteriaBuilder.createQuery(Appointment.class);
        Root<Appointment> appointmentRoot = criteriaQuery.from(Appointment.class);
        Metamodel m = entityManager.getMetamodel();
        EntityType<Appointment> appointmentMetaModel = m.entity(Appointment.class);
        Join<Appointment, Doctor> doctorRoot= appointmentRoot.join("doctor", JoinType.INNER);
        Set<Predicate> predicates = new HashSet<>(2);

        Predicate doctorPredicate=null;
        Predicate ovelapTimePredicate1 = null;
        Predicate ovelapTimePredicate2 = null;
        Predicate ovelapTimePredicate3 = null;
        Predicate ovelapTimePredicate4 = null;

        if(appointmentDto.getDoctorId()!=null){
            doctorPredicate=criteriaBuilder.equal(doctorRoot.get("id"), appointmentDto.getDoctorId());
            predicates.add(doctorPredicate);
        }

        if(appointmentDto.getStartDateTime()!=null && appointmentDto.getEndDateTime()!=null) {

            ovelapTimePredicate1=criteriaBuilder.and(criteriaBuilder.le(appointmentRoot.get("startDateTime"), appointmentDto.getStartDateTime())
                                                    ,criteriaBuilder.le(appointmentRoot.get("startDateTime"), appointmentDto.getStartDateTime()));

            ovelapTimePredicate2 = criteriaBuilder.and(criteriaBuilder.ge(appointmentRoot.get("startDateTime"), appointmentDto.getStartDateTime()),
                                                        criteriaBuilder.le(appointmentRoot.get("endDateTime"), appointmentDto.getStartDateTime()));


            ovelapTimePredicate3 = criteriaBuilder.and(criteriaBuilder.ge(appointmentRoot.get("startDateTime"), appointmentDto.getStartDateTime()),
                                                        criteriaBuilder.ge(appointmentRoot.get("endDateTime"), appointmentDto.getStartDateTime()));

            ovelapTimePredicate4 = criteriaBuilder.and(criteriaBuilder.le(appointmentRoot.get("startDateTime"), appointmentDto.getStartDateTime()),

                                                        criteriaBuilder.ge(appointmentRoot.get("endDateTime"), appointmentDto.getStartDateTime()));

          predicates.add(criteriaBuilder.or(ovelapTimePredicate1,ovelapTimePredicate2,ovelapTimePredicate3,ovelapTimePredicate4));

        }

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

}
