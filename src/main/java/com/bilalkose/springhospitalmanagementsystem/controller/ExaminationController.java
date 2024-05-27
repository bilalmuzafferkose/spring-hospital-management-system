package com.bilalkose.springhospitalmanagementsystem.controller;

import com.bilalkose.springhospitalmanagementsystem.dto.ExaminationDto;
import com.bilalkose.springhospitalmanagementsystem.dto.MedicineDto;
import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateExaminationRequest;
import com.bilalkose.springhospitalmanagementsystem.service.ExaminationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/examination")
@CrossOrigin("*")
public class ExaminationController {
    private final ExaminationService examinationService;

    public ExaminationController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ExaminationDto>> getAllExaminations() {
        return ResponseEntity.ok(examinationService.getAllExaminations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExaminationDto> getExaminationDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(examinationService.getExaminationDtoById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<ExaminationDto> createExamination(@Valid @RequestBody CreateExaminationRequest createExaminationRequest) {
        return ResponseEntity.ok( examinationService.save(createExaminationRequest));
    }
}
