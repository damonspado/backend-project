//package th.ac.mju.itsci.example.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import th.ac.mju.itsci.example.model.Doctor;
//import th.ac.mju.itsci.example.service.DoctorService;
//
//import java.util.List;
//
//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//@RequestMapping("/api/doctors")
//public class DoctorController {
//
//    private final DoctorService doctorService;
//
//    @Autowired
//    public DoctorController(DoctorService doctorService) {
//        this.doctorService = doctorService;
//    }
//
//    // 1. ดึงรายชื่อแพทย์ทั้งหมด (สามารถกรองด้วย ?type=HUMAN_DOCTOR หรือ ?type=VET ได้)
//    @GetMapping
//    public ResponseEntity<List<Doctor>> getAllDoctors(@RequestParam(name = "type", required = false) String type) {
//        if (type != null && !type.isEmpty()) {
//            return ResponseEntity.ok(doctorService.getDoctorsByType(type));
//        }
//        return ResponseEntity.ok(doctorService.getAllDoctors());
//    }
//
//    // 2. ดึงข้อมูลแพทย์ตาม ID
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getDoctorById(@PathVariable(name = "id") Long id) {
//        Doctor doctor = doctorService.getDoctorById(id);
//        if (doctor == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
//        }
//        return ResponseEntity.ok(doctor);
//    }
//
//    // 3. เพิ่มแพทย์ใหม่
//    @PostMapping
//    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
//        Doctor savedDoctor = doctorService.saveDoctor(doctor);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
//    }
//
//    // 4. ลบข้อมูลแพทย์
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteDoctor(@PathVariable(name = "id") Long id) {
//        doctorService.deleteDoctor(id);
//        return ResponseEntity.noContent().build();
//    }
//}











package th.ac.mju.itsci.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import th.ac.mju.itsci.example.model.Doctor;
import th.ac.mju.itsci.example.service.DoctorService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors(@RequestParam(name = "type", required = false) String type) {
        if (type != null && !type.isEmpty()) {
            return ResponseEntity.ok(doctorService.getDoctorsByType(type));
        }
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable(name = "id") Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }
        return ResponseEntity.ok(doctor);
    }

    // 🔥 เพิ่ม Endpoint สำหรับ Login ของ Doctor
    @PostMapping("/login")
    public ResponseEntity<?> loginDoctor(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            Doctor doctor = doctorService.loginDoctor(email, password);
            
            // ส่งข้อมูลกลับ พร้อมแนบสถานะ status: "DOCTOR"
            return ResponseEntity.ok(Map.of(
                "id", doctor.getId(),
                "name", doctor.getName(),
                "email", doctor.getEmail(),
                "status", "DOCTOR",
                "doctorType", doctor.getDoctorType()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable(name = "id") Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}