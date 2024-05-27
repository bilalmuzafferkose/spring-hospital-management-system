package com.bilalkose.springhospitalmanagementsystem.service;

import com.bilalkose.springhospitalmanagementsystem.dto.BarcodeDto;
import com.bilalkose.springhospitalmanagementsystem.dto.PatientDto;
import com.bilalkose.springhospitalmanagementsystem.dto.converter.BarcodeDtoConverter;
import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateBarcodeRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.request.UpdateBarcodeRequest;
import com.bilalkose.springhospitalmanagementsystem.exception.BarcodeNotFoundException;
import com.bilalkose.springhospitalmanagementsystem.exception.MedicineNotFoundException;
import com.bilalkose.springhospitalmanagementsystem.model.*;
import com.bilalkose.springhospitalmanagementsystem.repository.BarcodeRepository;
import com.bilalkose.springhospitalmanagementsystem.repository.MedicineRepository;
import com.bilalkose.springhospitalmanagementsystem.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BarcodeService {
    private final BarcodeRepository barcodeRepository;
    private final BarcodeDtoConverter barcodeDtoConverter;
    private final PatientRepository patientRepository;
    private final MedicineRepository medicineRepository;
    private final PatientService patientService;

    public BarcodeService(BarcodeRepository barcodeRepository, BarcodeDtoConverter barcodeDtoConverter, PatientRepository patientRepository, MedicineRepository medicineRepository, PatientService patientService) {
        this.barcodeRepository = barcodeRepository;
        this.barcodeDtoConverter = barcodeDtoConverter;
        this.patientRepository = patientRepository;
        this.medicineRepository = medicineRepository;
        this.patientService = patientService;
    }

    public BarcodeDto getBarcodeDtoById(Long id) {
        Barcode barcode = barcodeRepository.findById(id)
                .orElseThrow(
                        () -> new BarcodeNotFoundException("Barcode could not find by id: " + id)
                );
        return this.barcodeDtoConverter.convert(barcode);
    }

    public BarcodeDto save(CreateBarcodeRequest createBarcodeRequest) {
        patientService.checkIfPatientIsNotExistsWithCitizenshipNumber(createBarcodeRequest.getPatientCitizenshipNumber());
        Barcode barcode = barcodeDtoConverter.convert(createBarcodeRequest);
        Barcode savedBarcode = barcodeRepository.save(barcode);
        log.info("Barcode Saved: " + createBarcodeRequest.getPatientCitizenshipNumber() + " | "
                + createBarcodeRequest.getExpirationDate());
        return this.barcodeDtoConverter.convert(savedBarcode);
    }

    public BarcodeDto addMedicine(Long id, UpdateBarcodeRequest updateBarcodeRequest) { //add medicine
        Barcode barcode = barcodeRepository.findById(id)
                .orElseThrow(() -> new BarcodeNotFoundException("Barcode not found by id: " + id));
        this.checkIfMedicineIsNotExistsByName(updateBarcodeRequest.getMedicineName());
        Medicine medicine = medicineRepository.findByName(updateBarcodeRequest.getMedicineName());
        medicine.setBarcode(barcode);

        barcode.getMedicineList().add(medicine);

        Barcode savedBarcode = barcodeRepository.save(barcode);
        log.info("Barcode Updated: " + updateBarcodeRequest.getMedicineName());
        return barcodeDtoConverter.convert(savedBarcode);
    }

    private void checkIfMedicineIsNotExistsByName(String name) {
        if (!this.medicineRepository.existsByName(name)) {
            throw new MedicineNotFoundException("Medicine not found : " + name);
        }
    }

    public List<BarcodeDto> getAllBarcodes() {
        List<Barcode> doctorList = barcodeRepository.findAll();
        return doctorList.stream()
                .map(barcodeDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public void deleteBarcode(Long id) {
        Barcode barcode = barcodeRepository.findById(id)
                .orElseThrow(() -> new BarcodeNotFoundException("Barcode not found by id: " + id));
        this.barcodeRepository.deleteById(barcode.getId());
        log.info("Barcode Deleted: " + barcode.getId());
    }
}
