package com.core.covid19.repos;

import com.core.covid19.models.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQuery;
import java.util.List;
import java.util.Set;

public interface PatientDoctorRepo extends JpaRepository<PatientDoctor, Integer> {

    PatientDoctor getDoctor(int patient);
    List<Account> getDoctors(int province);
    Person getDoctorPatient(int patient);

    @Query(
            value = "SELECT DISTINCT p.id, (select max(m.send_date) from message m where m.person_sender_id = p.id OR m.person_received_id = p.id) as message_date " +
                    "FROM person p, patient_doctor pd " +
                    "WHERE pd.patient = p.id AND pd.doctor = :idDoctor " +
                    "AND p.id IN (SELECT m.person_sender_id FROM message m) " +
                    "UNION \n" +
                    "SELECT DISTINCT p.id, (date '1900-01-01') as message_date " +
                    "FROM person p, patient_doctor pd " +
                    "WHERE pd.patient = p.id AND pd.doctor = :idDoctor " +
                    "AND p.id NOT IN " +
                    "(SELECT DISTINCT p.id FROM person p, patient_doctor pd WHERE pd.patient = p.id AND pd.doctor = :idDoctor\n" +
                    "AND p.id IN (SELECT m.person_sender_id FROM message m)) " +
                    "ORDER BY message_date DESC ",
            nativeQuery = true
    )
    List<Object[]> getPatients(@Param("idDoctor") Integer idDoctor);

    @Query(
            value = "SELECT DISTINCT p.id, (select max(m.send_date) from message m where m.person_sender_id = p.id OR m.person_received_id = p.id) as message_date " +
                    "FROM person p, account a, role_account ra " +
                    "WHERE (p.id IN (SELECT m.person_sender_id FROM message m) OR p.id IN (SELECT m.person_received_id FROM message m)) " +
                    "AND ra.account_id = a.id AND ra.role_id = (SELECT id from role WHERE name = 'CIVIL') AND a.person_id = p.id " +
                    "AND p.province = :idProvincia " +
                    "AND p.id != :idCoordinador " +
                    "UNION \n" +
                    "SELECT DISTINCT p.id, (date '1900-01-01') as message_date " +
                    "FROM person p, account a, role_account ra " +
                    "WHERE p.id NOT IN (SELECT p2.id FROM person p2 WHERE p2.id IN (SELECT m.person_sender_id FROM message m) OR p2.id IN (SELECT m.person_received_id FROM message m)) " +
                    "AND ra.account_id = a.id AND ra.role_id = (SELECT id from role WHERE name = 'CIVIL') AND a.person_id = p.id " +
                    "AND p.province = :idProvincia " +
                    "AND p.id != :idCoordinador " +
                    "ORDER BY message_date DESC ",
            nativeQuery = true
    )
    List<Object[]> getCoordinatorPatients(@Param("idCoordinador") Integer idCoordinador, @Param("idProvincia") Integer idProvincia);

    @Query(
            value = "SELECT DISTINCT p.id, (select max(m.send_date) from message m where m.person_sender_id = p.id OR m.person_received_id = p.id) as message_date " +
                    "FROM person p, account a, role_account ra " +
                    "WHERE (p.id IN (SELECT m.person_sender_id FROM message m) OR p.id IN (SELECT m.person_received_id FROM message m)) " +
                    "AND ra.account_id = a.id AND ra.role_id = (SELECT id from role WHERE name = 'CIVIL') AND a.person_id = p.id " +
                    "UNION " +
                    "SELECT DISTINCT p.id, (date '1900-01-01') as message_date " +
                    "FROM person p, account a, role_account ra " +
                    "WHERE p.id NOT IN (SELECT p2.id FROM person p2 WHERE p2.id IN (SELECT m.person_sender_id FROM message m) OR p2.id IN (SELECT m.person_received_id FROM message m)) " +
                    "AND ra.account_id = a.id AND ra.role_id = (SELECT id from role WHERE name = 'CIVIL') AND a.person_id = p.id " +
                    "ORDER BY message_date DESC ",
            nativeQuery = true
    )
    List<Object[]> getAdminPatients();

    @Query(
            value = "select doctor, count(1) from patient_doctor where doctor in :doctors group by doctor order by 2",
            nativeQuery = true
    )
    List<Object[]> getDoctorByCountPatients(List<Integer> doctors);

    @Query(
            value = "select pd.patient from patient_doctor pd " +
                    "join person p on pd.patient = p.id " +
                    "join hospital h on p.hospital = h.id " +
                    "where pd.doctor = :doctor and h.id = :hospital;",
            nativeQuery = true
    )
    List<Integer> getPatientsAsigns(int doctor, int hospital);
}
