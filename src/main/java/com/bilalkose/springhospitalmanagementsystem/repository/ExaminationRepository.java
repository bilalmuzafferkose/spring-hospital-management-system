package com.bilalkose.springhospitalmanagementsystem.repository;

import com.bilalkose.springhospitalmanagementsystem.model.Examination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {
}
