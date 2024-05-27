package com.bilalkose.springhospitalmanagementsystem.dto.converter;

import com.bilalkose.springhospitalmanagementsystem.dto.BarcodeDto;
import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateBarcodeRequest;
import com.bilalkose.springhospitalmanagementsystem.model.Barcode;
import com.bilalkose.springhospitalmanagementsystem.model.Patient;
import com.bilalkose.springhospitalmanagementsystem.repository.PatientRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BarcodeDtoConverter {

    private final PatientDtoConverter patientDtoConverter;
    private final MedicineDtoConverter medicineDtoConverter;
    private final PatientRepository patientRepository;

    public BarcodeDtoConverter(@Lazy PatientDtoConverter patientDtoConverter, @Lazy MedicineDtoConverter medicineDtoConverter, PatientRepository patientRepository) {
        this.patientDtoConverter = patientDtoConverter;
        this.medicineDtoConverter = medicineDtoConverter;
        this.patientRepository = patientRepository;
    }

    public BarcodeDto convert(Barcode barcode) {
        return BarcodeDto.builder()
                .id(barcode.getId())
                .medicineDtoList(medicineDtoConverter.convertMedicineListToMedicineDtoList(barcode.getMedicineList()))
                .patientDto(patientDtoConverter.convert(barcode.getPatient()))
                .expirationDate(barcode.getExpirationDate())
                .build();
    }

    public Barcode convert(BarcodeDto barcodeDto) {
        return Barcode.builder()
                .id(barcodeDto.getId())
                .medicineList(medicineDtoConverter.convertMedicineDtoListToMedicineList(barcodeDto.getMedicineDtoList()))
                .patient(patientDtoConverter.convert(barcodeDto.getPatientDto()))
                .expirationDate(barcodeDto.getExpirationDate())
                .build();
    }

    public Barcode convert(CreateBarcodeRequest createBarcodeRequest) {
        Patient patient = patientRepository.findByCitizenshipNumber(createBarcodeRequest.getPatientCitizenshipNumber());
        if (patient == null) {
            //patient repository optional
        }
        return Barcode.builder()
                .patient(patient)
                .expirationDate(createBarcodeRequest.getExpirationDate())
                .medicineList(new ArrayList<>())
                .build();
    }
}
