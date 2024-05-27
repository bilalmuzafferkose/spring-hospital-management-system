package com.bilalkose.springhospitalmanagementsystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateExaminationRequest {
    @NotNull
    private Long barcodeId;
    @NotNull
    private String patientCitizenshipNumber;
    @NotNull
    private String doctorCitizenshipNumber;
}
