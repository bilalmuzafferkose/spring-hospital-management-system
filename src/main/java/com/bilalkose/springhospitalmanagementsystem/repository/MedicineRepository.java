package com.bilalkose.springhospitalmanagementsystem.repository;

import com.bilalkose.springhospitalmanagementsystem.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    boolean existsByName(String name);
    Medicine findByName(String name);
}
