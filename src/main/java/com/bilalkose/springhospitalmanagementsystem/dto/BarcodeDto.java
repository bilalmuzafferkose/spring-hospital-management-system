package com.bilalkose.springhospitalmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class BarcodeDto {
    private Long id; //buraya id yerine barkod numarasi gelebilir
    private List<MedicineDto> medicineDtoList;
    private PatientDto patientDto;
    private LocalDate expirationDate;
}
