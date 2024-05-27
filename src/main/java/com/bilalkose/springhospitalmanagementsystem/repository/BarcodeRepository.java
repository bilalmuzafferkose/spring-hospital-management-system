package com.bilalkose.springhospitalmanagementsystem.repository;

import com.bilalkose.springhospitalmanagementsystem.model.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarcodeRepository extends JpaRepository<Barcode, Long> {
}
