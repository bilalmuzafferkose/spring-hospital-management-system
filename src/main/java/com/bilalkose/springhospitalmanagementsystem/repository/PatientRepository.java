package com.bilalkose.springhospitalmanagementsystem.repository;

import com.bilalkose.springhospitalmanagementsystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByCitizenshipNumber(String citizenShipNumber);
    Patient findByCitizenshipNumber(String citizenshipNumber);
}
