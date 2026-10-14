package th.ac.mju.itsci.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import th.ac.mju.itsci.example.model.Category;
import th.ac.mju.itsci.example.model.Doctor;
import th.ac.mju.itsci.example.model.Vaccine;
import th.ac.mju.itsci.example.repository.CategoryRepository;
import th.ac.mju.itsci.example.repository.DoctorRepository;
import th.ac.mju.itsci.example.service.VaccineService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {

    private final VaccineService vaccineService;
    private final CategoryRepository categoryRepository;
    private final DoctorRepository doctorRepository; 

    @Value("${server.port:8081}")
    private String serverPort;

    private final String UPLOAD_DIR = "uploads/";

    public VaccineController(VaccineService vaccineService, CategoryRepository categoryRepository, DoctorRepository doctorRepository) {
        this.vaccineService = vaccineService;
        this.categoryRepository = categoryRepository;
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public ResponseEntity<List<Vaccine>> getAllVaccines(@RequestParam(name = "category", required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return ResponseEntity.ok(vaccineService.getVaccinesByCategory(category));
        }
        return ResponseEntity.ok(vaccineService.getAllVaccines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVaccineById(@PathVariable(name = "id") Long id) {
        Vaccine vaccine = vaccineService.getVaccineById(id);
        if (vaccine == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaccine not found");
        }
        return ResponseEntity.ok(vaccine);
    }

    // 3. สร้างวัคซีนใหม่
 
    @PostMapping
    public ResponseEntity<?> createVaccine(
            @RequestPart("vaccine") Vaccine vaccine,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                vaccine.setImageUrl("http://localhost:" + serverPort + "/uploads/" + fileName);
            }

            if (vaccine.getCategory() != null) {
                String catId = vaccine.getCategory().getCategoryId();
                if (catId != null) {
                    Category category = categoryRepository.findById(catId).orElse(null);
                    vaccine.setCategory(category);
                }
            }

            if (vaccine.getDoctor() != null && vaccine.getDoctor().getId() != null) {
                Doctor doctor = doctorRepository.findById(vaccine.getDoctor().getId()).orElse(null);
                vaccine.setDoctor(doctor);
            } else {
                vaccine.setDoctor(null);
            }

            Vaccine savedVaccine = vaccineService.saveVaccine(vaccine);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVaccine);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving: " + e.getMessage());
        }
    }


    // 4. อัปเดตวัคซีน

    @PostMapping("/{id}/update")
    public ResponseEntity<?> updateVaccine(
            @PathVariable(name = "id") Long id,
            @RequestPart("vaccine") Vaccine updateData,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            Vaccine existingVaccine = vaccineService.getVaccineById(id);
            if (existingVaccine == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaccine not found");
            }

            existingVaccine.setName(updateData.getName());
            existingVaccine.setDescription(updateData.getDescription());
            existingVaccine.setPrice(updateData.getPrice());
            existingVaccine.setDiscountPrice(updateData.getDiscountPrice());

            if (updateData.getCategory() != null) {
                String catId = updateData.getCategory().getCategoryId();
                if (catId != null) {
                    Category category = categoryRepository.findById(catId).orElse(null);
                    existingVaccine.setCategory(category);
                }
            }

            if (updateData.getDoctor() != null && updateData.getDoctor().getId() != null) {
                Doctor doctor = doctorRepository.findById(updateData.getDoctor().getId()).orElse(null);
                existingVaccine.setDoctor(doctor);
            } else {
                existingVaccine.setDoctor(null);
            }

            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                existingVaccine.setImageUrl("http://localhost:" + serverPort + "/uploads/" + fileName);
            }

            Vaccine savedVaccine = vaccineService.saveVaccine(existingVaccine);
            return ResponseEntity.ok(savedVaccine);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating: " + e.getMessage());
        }
    }

    // 5. ลบวัคซีน
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaccine(@PathVariable(name = "id") Long id) {
        vaccineService.deleteVaccine(id);
        return ResponseEntity.noContent().build();
    }
}