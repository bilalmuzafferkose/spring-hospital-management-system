package com.bilalkose.springhospitalmanagementsystem.service;

import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateDoctorRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.DoctorDto;
import com.bilalkose.springhospitalmanagementsystem.dto.request.UpdateDoctorRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.converter.DoctorDtoConverter;
import com.bilalkose.springhospitalmanagementsystem.exception.AlreadyCreatedDoctorException;
import com.bilalkose.springhospitalmanagementsystem.exception.DoctorNotFoundException;
import com.bilalkose.springhospitalmanagementsystem.model.Doctor;
import com.bilalkose.springhospitalmanagementsystem.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorDtoConverter doctorDtoConverter;

    public DoctorService(DoctorRepository doctorRepository, DoctorDtoConverter doctorDtoConverter) {
        this.doctorRepository = doctorRepository;
        this.doctorDtoConverter = doctorDtoConverter;
    }

    public List<DoctorDto> getAllDoctors() {
        List<Doctor> doctorList = doctorRepository.findAll();
        return doctorList.stream()
                .map(doctorDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public DoctorDto getDoctorDtoById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(
                        () -> new DoctorNotFoundException("Doctor could not find by id: " + id)
                );
        return this.doctorDtoConverter.convert(doctor);
    }

    public Doctor getDoctor(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(
                        () -> new DoctorNotFoundException("Doctor could not find by id: " + id)
                );
    }

    public DoctorDto save(CreateDoctorRequest createDoctorRequest) {
        this.checkIfDoctorIsExistsByCitizenshipNumber(createDoctorRequest.getCitizenshipNumber());
        Doctor doctor = doctorDtoConverter.convert(createDoctorRequest);
        Doctor savedDoctor = doctorRepository.save(doctor);
        log.info("Doctor Saved: " + createDoctorRequest.getCitizenshipNumber() + " | "
                + createDoctorRequest.getName() + " | " + createDoctorRequest.getSurname() + " | " + createDoctorRequest.getEmail());
        return this.doctorDtoConverter.convert(savedDoctor);
    }

    public DoctorDto updateDoctor(String citizenshipNumber, UpdateDoctorRequest updateDoctorRequest) {
        Doctor doctor = doctorRepository.findByCitizenshipNumber(citizenshipNumber);
        doctor.setName(updateDoctorRequest.getName());
        doctor.setSurname(updateDoctorRequest.getSurname());
        doctor.setEmail(updateDoctorRequest.getEmail());

        Doctor savedDoctor = doctorRepository.save(doctor);
        log.info("Doctor Updated: " + updateDoctorRequest.getName() + " | " + updateDoctorRequest.getSurname() + " | " + updateDoctorRequest.getEmail());
        return doctorDtoConverter.convert(savedDoctor);
    }

    public void deleteDoctor(String citizenshipNumber) {
        this.checkIfDoctorIsNotExistsWithCitizenshipNumber(citizenshipNumber);
        Doctor doctor = doctorRepository.findByCitizenshipNumber(citizenshipNumber);
        this.doctorRepository.deleteById(doctor.getId());
        log.info("Doctor Deleted: " + doctor.getCitizenshipNumber() + " | "
                + doctor.getName() + " | " + doctor.getSurname());
    }

    private void checkIfDoctorIsNotExistsWithCitizenshipNumber(String citizenshipNumber) {
        if (!this.doctorRepository.existsByCitizenshipNumber(citizenshipNumber)) {
            throw new DoctorNotFoundException("There is no doctor for this citizenshipNumber : " + citizenshipNumber);
        }
    }

    private void checkIfDoctorIsExistsByCitizenshipNumber(String citizenshipNumber) {
        if (this.doctorRepository.existsByCitizenshipNumber(citizenshipNumber)) {
            throw new AlreadyCreatedDoctorException("Already created for this citizenshipNumber : " + citizenshipNumber);
        }
    }
}
