package com.bilalkose.springhospitalmanagementsystem.controller;

import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateMedicineRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.MedicineDto;
import com.bilalkose.springhospitalmanagementsystem.service.MedicineService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/medicine")
@CrossOrigin("*")
public class MedicineController {
    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicineDto>> getAllMedicines() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineDto> getMedicineDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineDtoById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<MedicineDto> createMedicine(@Valid @RequestBody CreateMedicineRequest createMedicineRequest) {
        return ResponseEntity.ok( medicineService.save(createMedicineRequest));
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteMedicine(@PathVariable String name) {
        medicineService.deleteMedicine(name);
        return ResponseEntity.ok("Medicine with citizenshipNumber " + name + " is deleted.");
    }
}
