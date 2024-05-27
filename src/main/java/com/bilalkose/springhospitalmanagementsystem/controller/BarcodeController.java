package com.bilalkose.springhospitalmanagementsystem.controller;

import com.bilalkose.springhospitalmanagementsystem.dto.BarcodeDto;
import com.bilalkose.springhospitalmanagementsystem.dto.DoctorDto;
import com.bilalkose.springhospitalmanagementsystem.dto.PatientDto;
import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateBarcodeRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.request.UpdateBarcodeRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.request.UpdateDoctorRequest;
import com.bilalkose.springhospitalmanagementsystem.service.BarcodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/barcode")
@CrossOrigin("*")
public class BarcodeController {
    private final BarcodeService barcodeService;

    public BarcodeController(BarcodeService barcodeService) {
        this.barcodeService = barcodeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarcodeDto> getBarcodeDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(barcodeService.getBarcodeDtoById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BarcodeDto>> getAllBarcodes() {
        return ResponseEntity.ok(barcodeService.getAllBarcodes());
    }

    @PostMapping("/save")
    public ResponseEntity<BarcodeDto> createBarcode(@Valid @RequestBody CreateBarcodeRequest createBarcodeRequest) {
        return ResponseEntity.ok( barcodeService.save(createBarcodeRequest));
    }

    @PutMapping("/addMedicine/{id}")
    public ResponseEntity<BarcodeDto> addMedicine(@Valid @PathVariable Long id, @RequestBody UpdateBarcodeRequest updateBarcodeRequest) {
        return ResponseEntity.ok(barcodeService.addMedicine(id, updateBarcodeRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBarcode(@PathVariable Long id) {
        barcodeService.deleteBarcode(id);
        return ResponseEntity.ok("Barcode with id " + id + " is deleted.");
    }

}
