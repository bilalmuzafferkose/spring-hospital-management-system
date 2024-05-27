package com.bilalkose.springhospitalmanagementsystem.service;

import com.bilalkose.springhospitalmanagementsystem.dto.ExaminationDto;
import com.bilalkose.springhospitalmanagementsystem.dto.converter.ExaminationDtoConverter;
import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateExaminationRequest;
import com.bilalkose.springhospitalmanagementsystem.exception.BarcodeNotFoundException;
import com.bilalkose.springhospitalmanagementsystem.exception.ExaminationNotFoundException;
import com.bilalkose.springhospitalmanagementsystem.exception.MedicineNotFoundException;
import com.bilalkose.springhospitalmanagementsystem.model.Barcode;
import com.bilalkose.springhospitalmanagementsystem.model.Doctor;
import com.bilalkose.springhospitalmanagementsystem.model.Examination;
import com.bilalkose.springhospitalmanagementsystem.repository.BarcodeRepository;
import com.bilalkose.springhospitalmanagementsystem.repository.DoctorRepository;
import com.bilalkose.springhospitalmanagementsystem.repository.ExaminationRepository;
import com.bilalkose.springhospitalmanagementsystem.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExaminationService {
    private final ExaminationRepository examinationRepository;
    private final ExaminationDtoConverter examinationDtoConverter;
    private final BarcodeRepository barcodeRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public ExaminationService(ExaminationRepository examinationRepository, ExaminationDtoConverter examinationDtoConverter, BarcodeRepository barcodeRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.examinationRepository = examinationRepository;
        this.examinationDtoConverter = examinationDtoConverter;
        this.barcodeRepository = barcodeRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public ExaminationDto getExaminationDtoById(Long id) {
        Examination examination = examinationRepository.findById(id)
                .orElseThrow(
                        () -> new ExaminationNotFoundException("Examination could not find by id: " + id)
                );
        return this.examinationDtoConverter.convert(examination);
    }

    public ExaminationDto save(CreateExaminationRequest createExaminationRequest) {
        Barcode barcode = barcodeRepository.findById(createExaminationRequest.getBarcodeId())
                .orElseThrow(() -> new BarcodeNotFoundException("Barcode not found by id: " + createExaminationRequest.getBarcodeId()));
        this.checkIfPatientIsNotExistsByCitizenshipNumber(createExaminationRequest.getPatientCitizenshipNumber());
        this.checkIfDoctorIsNotExistsByCitizenshipNumber(createExaminationRequest.getDoctorCitizenshipNumber());
        Examination examination = examinationDtoConverter.convert(createExaminationRequest);
        Examination savedExamination = examinationRepository.save(examination);
        log.info("Examination Saved: " + createExaminationRequest.getBarcodeId() + " | " + createExaminationRequest.getPatientCitizenshipNumber() + " | "
                + createExaminationRequest.getDoctorCitizenshipNumber());
        return this.examinationDtoConverter.convert(savedExamination);
    }

    public List<ExaminationDto> getAllExaminations() {
        List<Examination> examinationList = examinationRepository.findAll();
        return examinationList.stream()
                .map(examinationDtoConverter::convert)
                .collect(Collectors.toList());
    }

    private void checkIfPatientIsNotExistsByCitizenshipNumber(String citizenshipNumber) {
        if (!this.patientRepository.existsByCitizenshipNumber(citizenshipNumber)) {
            throw new MedicineNotFoundException("Patient not found : " + citizenshipNumber);
        }
    }

    private void checkIfDoctorIsNotExistsByCitizenshipNumber(String citizenshipNumber) {
        if (!this.doctorRepository.existsByCitizenshipNumber(citizenshipNumber)) {
            throw new MedicineNotFoundException("Doctor not found : " + citizenshipNumber);
        }
    }
}
