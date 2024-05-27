package com.bilalkose.springhospitalmanagementsystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBarcodeRequest {
    @NotNull
    private String patientCitizenshipNumber;
    private LocalDate expirationDate;
}
