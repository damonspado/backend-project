//package th.ac.mju.itsci.example.service;
//
//import th.ac.mju.itsci.example.model.Vaccine;
//import th.ac.mju.itsci.example.repository.VaccineRepository;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class VaccineService {
//
//    private final VaccineRepository vaccineRepository;
//
//    public VaccineService(VaccineRepository vaccineRepository) {
//        this.vaccineRepository = vaccineRepository;
//    }
//
//    public List<Vaccine> getAllVaccines() {
//        return vaccineRepository.findAll();
//    }
//
//    public List<Vaccine> getVaccinesByCategory(String categoryId) {
//        return vaccineRepository.findByCategoryCategoryId(categoryId);
//    }
//
//    public Vaccine getVaccineById(Long id) {
//        return vaccineRepository.findById(id).orElse(null);
//    }
//
//    public Vaccine saveVaccine(Vaccine vaccine) {
//        return vaccineRepository.save(vaccine);
//    }
//
//    public void deleteVaccine(Long id) {
//        vaccineRepository.deleteById(id);
//    }
//}









package th.ac.mju.itsci.example.service;

import th.ac.mju.itsci.example.model.Vaccine;
import th.ac.mju.itsci.example.model.Doctor;
import th.ac.mju.itsci.example.repository.VaccineRepository;
import th.ac.mju.itsci.example.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VaccineService {

    private final VaccineRepository vaccineRepository;
    private final DoctorRepository doctorRepository;

    public VaccineService(VaccineRepository vaccineRepository, DoctorRepository doctorRepository) {
        this.vaccineRepository = vaccineRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    public List<Vaccine> getVaccinesByCategory(String categoryId) {
        return vaccineRepository.findByCategoryCategoryId(categoryId);
    }

    public Vaccine getVaccineById(Long id) {
        return vaccineRepository.findById(id).orElse(null);
    }

    public Vaccine saveVaccine(Vaccine vaccine) {
        // 🔥 ตรวจสอบและผูกข้อมูล Doctor หากมีการส่ง doctorId หรือ doctor object มา
        if (vaccine.getDoctor() != null && vaccine.getDoctor().getId() != null) {
            Doctor doctor = doctorRepository.findById(vaccine.getDoctor().getId()).orElse(null);
            vaccine.setDoctor(doctor);
        } else {
            vaccine.setDoctor(null);
        }
        return vaccineRepository.save(vaccine);
    }

    public void deleteVaccine(Long id) {
        vaccineRepository.deleteById(id);
    }
}