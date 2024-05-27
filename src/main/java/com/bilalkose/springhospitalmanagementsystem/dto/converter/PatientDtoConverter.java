package com.bilalkose.springhospitalmanagementsystem.dto.converter;

import com.bilalkose.springhospitalmanagementsystem.dto.request.CreatePatientRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.PatientDto;
import com.bilalkose.springhospitalmanagementsystem.dto.request.UpdatePatientRequest;
import com.bilalkose.springhospitalmanagementsystem.model.Patient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PatientDtoConverter {

    private final ExaminationDtoConverter examinationDtoConverter;

    public PatientDtoConverter(@Lazy ExaminationDtoConverter examinationDtoConverter) {
        this.examinationDtoConverter = examinationDtoConverter;
    }

    public PatientDto convert(Patient patient) {
        return PatientDto.builder()
                .citizenshipNumber(patient.getCitizenshipNumber())
                .name(patient.getName())
                .surname(patient.getSurname())
                .email(patient.getEmail())
                .birthday(patient.getBirthday())
                .build();
    }

    public Patient convert(PatientDto patientDto) {
        return Patient.builder()
                .citizenshipNumber(patientDto.getCitizenshipNumber())
                .name(patientDto.getName())
                .surname(patientDto.getSurname())
                .email(patientDto.getEmail())
                .birthday(patientDto.getBirthday())
                .build();
    }

    public Patient convert(CreatePatientRequest createPatientRequest) {
        return Patient.builder()
                .citizenshipNumber(createPatientRequest.getCitizenshipNumber())
                .name(createPatientRequest.getName())
                .surname(createPatientRequest.getSurname())
                .email(createPatientRequest.getEmail())
                .birthday(createPatientRequest.getBirthday())
                .build();
    }
}
