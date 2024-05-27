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
public class PatientDto {
    private String citizenshipNumber;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthday;
    private List<ExaminationDto> examinationList;
}
