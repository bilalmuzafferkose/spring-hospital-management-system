package com.bilalkose.springhospitalmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class DoctorDto {
    private String citizenshipNumber;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthday;
}

