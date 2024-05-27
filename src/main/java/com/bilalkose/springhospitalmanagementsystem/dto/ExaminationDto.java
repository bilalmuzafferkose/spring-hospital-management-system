package com.bilalkose.springhospitalmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class ExaminationDto {
    private BarcodeDto barcodeDto;
    private PatientDto patientDto;
    private DoctorDto doctorDto;
}
