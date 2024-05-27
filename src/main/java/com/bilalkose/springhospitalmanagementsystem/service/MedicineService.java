package com.bilalkose.springhospitalmanagementsystem.service;

import com.bilalkose.springhospitalmanagementsystem.dto.request.CreateMedicineRequest;
import com.bilalkose.springhospitalmanagementsystem.dto.MedicineDto;
import com.bilalkose.springhospitalmanagementsystem.dto.converter.MedicineDtoConverter;
import com.bilalkose.springhospitalmanagementsystem.exception.AlreadyCreatedMedicineException;
import com.bilalkose.springhospitalmanagementsystem.exception.MedicineNotFoundException;
import com.bilalkose.springhospitalmanagementsystem.model.Medicine;
import com.bilalkose.springhospitalmanagementsystem.repository.MedicineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final MedicineDtoConverter medicineDtoConverter;

    public MedicineService(MedicineRepository medicineRepository, MedicineDtoConverter medicineDtoConverter) {
        this.medicineRepository = medicineRepository;
        this.medicineDtoConverter = medicineDtoConverter;
    }

    public List<MedicineDto> getAllMedicines() {
        List<Medicine> medicineList = medicineRepository.findAll();
        return medicineList.stream()
                .map(medicineDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public MedicineDto getMedicineDtoById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(
                        () -> new MedicineNotFoundException("Medicine could not find by id: " + id)
                );
        return this.medicineDtoConverter.convert(medicine);
    }

    public MedicineDto save(CreateMedicineRequest createMedicineRequest) {
        this.checkIfMedicineIsExistsByName(createMedicineRequest.getName());
        Medicine medicine = medicineDtoConverter.convert(createMedicineRequest);
        Medicine savedMedicine = medicineRepository.save(medicine);
        log.info("Medicine Saved: " + createMedicineRequest.getName());
        return this.medicineDtoConverter.convert(savedMedicine);
    }

    public void deleteMedicine(String name) {
        this.checkIfMedicineIsNotExistsWithName(name);
        Medicine medicine = medicineRepository.findByName(name);
        this.medicineRepository.deleteById(medicine.getId());
        log.info("Medicine Deleted: " + medicine.getName());
    }

    private void checkIfMedicineIsNotExistsWithName(String name) {
        if (!this.medicineRepository.existsByName(name)) {
            throw new MedicineNotFoundException("There is no medicine for this name : " + name);
        }
    }

    private void checkIfMedicineIsExistsByName(String name) {
        if (this.medicineRepository.existsByName(name)) {
            throw new AlreadyCreatedMedicineException("Already created for this name : " + name);
        }
    }
}
