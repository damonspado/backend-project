package th.ac.mju.itsci.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import th.ac.mju.itsci.example.model.Doctor;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByDoctorType(String doctorType);
    Optional<Doctor> findByEmail(String email); // เพิ่มตัวนี้
}