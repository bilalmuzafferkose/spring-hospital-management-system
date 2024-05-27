package com.bilalkose.springhospitalmanagementsystem.dto.converter;

import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateDoctorRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.DoctorDto;
import com.bilalkose.springhospitalmanagementsystem.model.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorDtoConverter {

    public DoctorDto convert(Doctor doctor) {
        return DoctorDto.builder()
                .citizenshipNumber(doctor.getCitizenshipNumber())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .birthday(doctor.getBirthday())
                .build();
    }

    public Doctor convert(DoctorDto doctorDto) {
        return Doctor.builder()
                .citizenshipNumber(doctorDto.getCitizenshipNumber())
                .name(doctorDto.getName())
                .surname(doctorDto.getSurname())
                .email(doctorDto.getEmail())
                .birthday(doctorDto.getBirthday())
                .build();
    }

    public Doctor convert(CreateDoctorRequest createDoctorRequest) {
        return Doctor.builder()
                .citizenshipNumber(createDoctorRequest.getCitizenshipNumber())
                .name(createDoctorRequest.getName())
                .surname(createDoctorRequest.getSurname())
                .email(createDoctorRequest.getEmail())
                .build();
    }
}
