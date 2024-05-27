package com.bilalkose.springhospitalmanagementsystem.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMedicineRequest {
    @NotNull
    @Size(min = 2, max = 30, message = "Name must be between at 2 and 30 characters long")
    private String name;
}
