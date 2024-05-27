package com.bilalkose.springhospitalmanagementsystem.dto.converter;

import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateMedicineRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.MedicineDto;
import com.bilalkose.springhospitalmanagementsystem.model.Medicine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicineDtoConverter {
    public MedicineDto convert(Medicine medicine) {
        return MedicineDto.builder()
                .name(medicine.getName())
                .build();
    }

    public Medicine convert(CreateMedicineRequest createMedicineRequest) {
        return Medicine.builder()
                .name(createMedicineRequest.getName())
                .build();
    }

    public List<MedicineDto> convertMedicineListToMedicineDtoList(List<Medicine> medicines) {

        return medicines.stream()
                .map(this::convertMedicineToMedicineDto)
                .collect(Collectors.toList());
    }

    public MedicineDto convertMedicineToMedicineDto(Medicine medicine) {
        return new MedicineDto(medicine.getName());
    }

    public List<Medicine> convertMedicineDtoListToMedicineList(List<MedicineDto> medicineDtos) {

        return medicineDtos.stream()
                .map(this::convertMedicineDtoToMedicine)
                .collect(Collectors.toList());
    }

    public Medicine convertMedicineDtoToMedicine(MedicineDto medicineDto) {
        Medicine medicine = new Medicine();
        medicine.setName(medicineDto.getName());
        return medicine;
    }
}
