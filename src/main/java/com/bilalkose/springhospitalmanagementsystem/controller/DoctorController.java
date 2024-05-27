package com.bilalkose.springhospitalmanagementsystem.controller;

import com.bilalkose.springhospitalmanagementsystem.dto.*;
import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateDoctorRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.request.UpdateDoctorRequest;
import com.bilalkose.springhospitalmanagementsystem.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/doctor")
@CrossOrigin("*")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorDtoById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<DoctorDto> createPatient(@Valid @RequestBody CreateDoctorRequest createDoctorRequest) {
        return ResponseEntity.ok( doctorService.save(createDoctorRequest));
    }

    @PutMapping("/update/{citizenshipNumber}")
    public ResponseEntity<DoctorDto> updateDoctor(@Valid @PathVariable String citizenshipNumber, @RequestBody UpdateDoctorRequest updateDoctorRequest) {
        return ResponseEntity.ok(doctorService.updateDoctor(citizenshipNumber, updateDoctorRequest));
    }

    @DeleteMapping("/delete/{citizenshipNumber}")
    public ResponseEntity<String> deleteDoctor(@PathVariable String citizenshipNumber) {
        doctorService.deleteDoctor(citizenshipNumber);
        return ResponseEntity.ok("Doctor with citizenshipNumber " + citizenshipNumber + " is deleted.");
    }
}
