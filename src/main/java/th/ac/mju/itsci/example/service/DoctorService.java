package th.ac.mju.itsci.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.ac.mju.itsci.example.model.Doctor;
import th.ac.mju.itsci.example.repository.DoctorRepository;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors() { return doctorRepository.findAll(); }
    public List<Doctor> getDoctorsByType(String doctorType) { return doctorRepository.findByDoctorType(doctorType); }
    public Doctor getDoctorById(Long id) { return doctorRepository.findById(id).orElse(null); }
    public Doctor saveDoctor(Doctor doctor) { return doctorRepository.save(doctor); }
    public void deleteDoctor(Long id) { doctorRepository.deleteById(id); }

    // 🔥 เพิ่มระบบ Login สำหรับ Doctor
    public Doctor loginDoctor(String email, String password) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบอีเมลนี้ในระบบแพทย์"));
        
        if (!doctor.getPassword().equals(password)) {
            throw new IllegalArgumentException("รหัสผ่านไม่ถูกต้อง");
        }
        return doctor;
    }
}