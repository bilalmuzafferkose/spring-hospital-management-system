package com.bilalkose.springhospitalmanagementsystem.controller;

import com.bilalkose.springhospitalmanagementsystem.dto.request.CreatePatientRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.PatientDto;
import com.bilalkose.springhospitalmanagementsystem.dto.request.UpdatePatientRequest;
import com.bilalkose.springhospitalmanagementsystem.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/patient")
@CrossOrigin("*")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientDtoById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody CreatePatientRequest createPatientRequest) {
        return ResponseEntity.ok( patientService.save(createPatientRequest));
    }

    @PutMapping("/update/{citizenshipNumber}")
    public ResponseEntity<PatientDto> updatePatient(@Valid @PathVariable String citizenshipNumber, @RequestBody UpdatePatientRequest updatePatientRequest) {
        return ResponseEntity.ok(patientService.updatePatient(citizenshipNumber, updatePatientRequest));
    }

    @DeleteMapping("/delete/{citizenshipNumber}")
    public ResponseEntity<String> deletePatient(@PathVariable String citizenshipNumber) {
        patientService.deletePatient(citizenshipNumber);
        return ResponseEntity.ok("Patient with citizenshipNumber " + citizenshipNumber + " is deleted.");
    }
}
