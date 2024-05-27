package com.bilalkose.springhospitalmanagementsystem.dto.converter;

import com.bilalkose.springhospitalmanagementsystem.dto.ExaminationDto;
import com.bilalkose.springhospitalmanagementsystem.dto.MedicineDto;
import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateExaminationRequest;
import com.bilalkose.springhospitalmanagementsystem.exception.PatientNotFoundException;
import com.bilalkose.springhospitalmanagementsystem.model.*;
import com.bilalkose.springhospitalmanagementsystem.repository.BarcodeRepository;
import com.bilalkose.springhospitalmanagementsystem.repository.DoctorRepository;
import com.bilalkose.springhospitalmanagementsystem.repository.PatientRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExaminationDtoConverter {
    private final BarcodeDtoConverter barcodeDtoConverter;
    private final PatientDtoConverter patientDtoConverter;
    private final DoctorDtoConverter doctorDtoConverter;
    private final BarcodeRepository barcodeRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public ExaminationDtoConverter(@Lazy BarcodeDtoConverter barcodeDtoConverter, @Lazy PatientDtoConverter patientDtoConverter, @Lazy DoctorDtoConverter doctorDtoConverter, BarcodeRepository barcodeRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.barcodeDtoConverter = barcodeDtoConverter;
        this.patientDtoConverter = patientDtoConverter;
        this.doctorDtoConverter = doctorDtoConverter;
        this.barcodeRepository = barcodeRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public ExaminationDto convert(Examination examination) {
        return ExaminationDto.builder()
                .barcodeDto(barcodeDtoConverter.convert(examination.getBarcode()))
                .patientDto(patientDtoConverter.convert(examination.getPatient()))
                .doctorDto(doctorDtoConverter.convert(examination.getDoctor()))
                .build();

    }

    public Examination convert(CreateExaminationRequest createExaminationRequest) {
        Barcode barcode = barcodeRepository.findById(createExaminationRequest.getBarcodeId())
                .orElseThrow(
                        () -> new PatientNotFoundException("Barcode could not find by id: " + createExaminationRequest.getBarcodeId())
                );
        Patient patient = patientRepository.findByCitizenshipNumber(createExaminationRequest.getPatientCitizenshipNumber()); //service den handle edildi
        Doctor doctor = doctorRepository.findByCitizenshipNumber(createExaminationRequest.getDoctorCitizenshipNumber()); //service den handle edildi

        return Examination.builder()
                .barcode(barcode)
                .patient(patient)
                .doctor(doctor)
                .build();
    }

    public Examination convertExaminationDtoToExamination(ExaminationDto examinationDto) {
        return Examination.builder()
                .barcode(barcodeDtoConverter.convert(examinationDto.getBarcodeDto()))
                .patient(patientDtoConverter.convert(examinationDto.getPatientDto()))
                .doctor(doctorDtoConverter.convert(examinationDto.getDoctorDto()))
                .build();
    }
}
