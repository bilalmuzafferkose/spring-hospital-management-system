package com.bilalkose.springhospitalmanagementsystem.service;

import com.bilalkose.springhospitalmanagementsystem.dto.request.CreatePatientRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.PatientDto;
import com.bilalkose.springhospitalmanagementsystem.dto.request.UpdatePatientRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.converter.PatientDtoConverter;
import com.bilalkose.springhospitalmanagementsystem.exception.AlreadyCreatedPatientException;
import com.bilalkose.springhospitalmanagementsystem.exception.PatientNotFoundException;
import com.bilalkose.springhospitalmanagementsystem.model.Patient;
import com.bilalkose.springhospitalmanagementsystem.producer.RabbitMQProducer;
import com.bilalkose.springhospitalmanagementsystem.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientDtoConverter patientDtoConverter;
    private final RabbitMQProducer rabbitMQProducer;

    public PatientService(PatientRepository patientRepository, PatientDtoConverter patientDtoConverter, RabbitMQProducer rabbitMQProducer) {
        this.patientRepository = patientRepository;
        this.patientDtoConverter = patientDtoConverter;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public List<PatientDto> getAllPatients() {
        List<Patient> patientList = patientRepository.findAll();
        return patientList.stream()
                .map(patientDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public PatientDto getPatientDtoById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(
                        () -> new PatientNotFoundException("Patient could not find by id: " + id)
                );
        return this.patientDtoConverter.convert(patient);
    }

    public Patient getPatient(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(
                        () -> new PatientNotFoundException("Patient could not find by id: " + id)
                );
    }

    public PatientDto save(CreatePatientRequest createPatientRequest) {
        this.checkIfPatientIsExistsByCitizenshipNumber(createPatientRequest.getCitizenshipNumber());
        Patient patient = patientDtoConverter.convert(createPatientRequest);
        Patient savedPatient = patientRepository.save(patient);
        log.info("Patient Saved: " + createPatientRequest.getCitizenshipNumber() + " | "
                + createPatientRequest.getName() + " | " + createPatientRequest.getSurname() + " | " + createPatientRequest.getEmail());
        rabbitMQProducer.sendWelcomeEmailToQueue(savedPatient.getEmail());
        return this.patientDtoConverter.convert(savedPatient);
    }

    public PatientDto updatePatient(String citizenshipNumber, UpdatePatientRequest updatePatientRequest) {
        Patient patient = patientRepository.findByCitizenshipNumber(citizenshipNumber);

        patient.setName(updatePatientRequest.getName());
        patient.setSurname(updatePatientRequest.getSurname());
        patient.setEmail(updatePatientRequest.getEmail());

        Patient savedPatient = patientRepository.save(patient);
        log.info("Patient Updated: " + updatePatientRequest.getName() + " | " + updatePatientRequest.getSurname() + " | " + updatePatientRequest.getEmail());
        return patientDtoConverter.convert(savedPatient);
    }

    public void deletePatient(String citizenshipNumber) {
        this.checkIfPatientIsNotExistsWithCitizenshipNumber(citizenshipNumber);
        Patient patient = patientRepository.findByCitizenshipNumber(citizenshipNumber);
        this.patientRepository.deleteById(patient.getId());
        log.info("Patient Deleted: " + patient.getCitizenshipNumber() + " | "
                + patient.getName() + " | " + patient.getSurname());
    }

    public void checkIfPatientIsNotExistsWithCitizenshipNumber(String citizenshipNumber) {
        if (!this.patientRepository.existsByCitizenshipNumber(citizenshipNumber)) {
            throw new PatientNotFoundException("There is no patient for this citizenshipNumber : " + citizenshipNumber);
        }
    }

    public void checkIfPatientIsExistsByCitizenshipNumber(String citizenshipNumber) {
        if (this.patientRepository.existsByCitizenshipNumber(citizenshipNumber)) {
            throw new AlreadyCreatedPatientException("Already created for this citizenshipNumber : " + citizenshipNumber);
        }
    }
}
