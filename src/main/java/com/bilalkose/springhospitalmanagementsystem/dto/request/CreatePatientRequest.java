package com.bilalkose.springhospitalmanagementsystem.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientRequest {
    @NotNull
    @Size(min = 11, max = 11, message = "Citizenship number must be exactly 11 characters long")
    @Column(unique = true)
    private String citizenshipNumber;
    @NotNull
    @Size(min = 2, max = 30, message = "Name must be between at 2 and 30 characters long")
    private String name;
    @NotNull
    @Size(min = 2, max = 11, message = "Surname must be between at 2 and 30 characters long")
    private String surname;
    @Column(unique = true)
    @Email
    private String email;
    private LocalDate birthday;
}
