package th.ac.mju.itsci.example.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import th.ac.mju.itsci.example.model.Appointment;
import th.ac.mju.itsci.example.repository.AppointmentRepository;
import th.ac.mju.itsci.example.service.AppointmentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentService appointmentService, AppointmentRepository appointmentRepository) {
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments(
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestParam(name = "status", required = false) String status) {
        if (userId != null) {
            if (status != null && !status.isEmpty()) {
                return ResponseEntity.ok(appointmentService.getUserAppointmentsByStatus(userId, status));
            }
            return ResponseEntity.ok(appointmentService.getUserAppointments(userId));
        }
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Map<String, Object> payload) {
        try {
            Long userId = Long.valueOf(payload.get("userId").toString());
            Long vaccineId = Long.valueOf(payload.get("vaccineId").toString());
            Long recipientId = Long.valueOf(payload.get("recipientId").toString());
            LocalDate appointmentDate = LocalDate.parse(payload.get("appointmentDate").toString());
            String timeSlot = payload.get("timeSlot").toString();
            String notes = payload.get("notes") != null ? payload.get("notes").toString() : null;

            Appointment created = appointmentService.createAppointment(
                    userId, vaccineId, recipientId, appointmentDate, timeSlot, notes
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // มอบหมายแพทย์และยืนยันคิว
    @PutMapping("/{id}/assign-doctor")
    public ResponseEntity<?> assignDoctor(
            @PathVariable(name = "id") Long id,
            @RequestBody Map<String, Object> payload) {
        try {
            Long doctorId = Long.valueOf(payload.get("doctorId").toString());
            Appointment updated = appointmentService.assignDoctor(id, doctorId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // 🔥 แก้ไขแล้ว: ให้รับชื่อแพทย์ (staffName) มาเซฟลง Database ด้วย 🔥
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateAppointmentStatus(
            @PathVariable(name = "id") Long id,
            @RequestBody Map<String, Object> payload) {
        try {
            Appointment appointment = appointmentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ไม่พบนัดหมาย"));
            
            // อัปเดตสถานะ (เช่น เปลี่ยนเป็น COMPLETED)
            if (payload.containsKey("status")) {
                appointment.setStatus(payload.get("status").toString());
            }
            
            // 🔥 ดึงชื่อหมอที่ส่งมาจากหน้าเว็บ และเซฟลง Object Appointment
            if (payload.containsKey("staffName") && payload.get("staffName") != null) {
                appointment.setStaffName(payload.get("staffName").toString());
            }
            
            Appointment updated = appointmentRepository.save(appointment);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable(name = "id") Long id) {
        try {
            Appointment cancelled = appointmentService.cancelAppointment(id);
            return ResponseEntity.ok(cancelled);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}