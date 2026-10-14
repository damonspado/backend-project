package th.ac.mju.itsci.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import th.ac.mju.itsci.example.model.Appointment;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    // ดึงประวัติการจองทั้งหมดของ User เรียงจากใหม่ไปเก่า
    List<Appointment> findByUserIdOrderByCreatedAtDesc(Long userId);

    // ดึงการจองของ User พร้อมกรองตามสถานะ (เช่น PENDING, CONFIRMED)
    List<Appointment> findByUserIdAndStatusOrderByAppointmentDateAsc(Long userId, String status);

    // ค้นหาด้วยรหัสอ้างอิงการจอง เช่น "APT-..."
    Optional<Appointment> findByAppointmentCode(String appointmentCode);

    // ตรวจสอบจำนวนคิวในรอบวัน-เวลานั้นๆ (นำไปใช้เช็คว่ารอบเต็มหรือยัง)
    long countByAppointmentDateAndTimeSlot(LocalDate appointmentDate, String timeSlot);
}