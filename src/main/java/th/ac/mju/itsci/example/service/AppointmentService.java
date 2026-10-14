package th.ac.mju.itsci.example.service;

import org.springframework.stereotype.Service;
import th.ac.mju.itsci.example.model.*;
import th.ac.mju.itsci.example.repository.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final VaccineRepository vaccineRepository;
    private final RecipientRepository recipientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              VaccineRepository vaccineRepository,
                              RecipientRepository recipientRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.vaccineRepository = vaccineRepository;
        this.recipientRepository = recipientRepository;
        this.doctorRepository = doctorRepository;
    }

    // ดึงประวัติการจองของผู้ใช้
    public List<Appointment> getUserAppointments(Long userId) {
        return appointmentRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // ดึงการจองของผู้ใช้แบบแยกสถานะ
    public List<Appointment> getUserAppointmentsByStatus(Long userId, String status) {
        return appointmentRepository.findByUserIdAndStatusOrderByAppointmentDateAsc(userId, status);
    }

    // ดึงข้อมูลนัดหมายทั้งหมด (สำหรับฝั่ง Admin)
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // สร้างการจองนัดหมายใหม่
    public Appointment createAppointment(Long userId, Long vaccineId, Long recipientId,
                                         LocalDate appointmentDate, String timeSlot, String notes) {
        // จำกัดรอบละไม่เกิน 10 คิว
        long currentBookings = appointmentRepository.countByAppointmentDateAndTimeSlot(appointmentDate, timeSlot);
        if (currentBookings >= 10) {
            throw new IllegalStateException("รอบเวลานี้เต็มแล้ว กรุณาเลือกรอบเวลาอื่น");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบผู้ใช้งาน"));
        Vaccine vaccine = vaccineRepository.findById(vaccineId)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบข้อมูลวัคซีน"));
        Recipient recipient = recipientRepository.findById(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบข้อมูลผู้รับวัคซีน"));

        // คำนวณราคาจาก discountPrice ถ้ามี หากไม่มีให้ใช้ราคาเต็มปกติ
        double finalPrice = vaccine.getDiscountPrice() > 0 ? vaccine.getDiscountPrice() : vaccine.getPrice();

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setVaccine(vaccine);
        appointment.setRecipient(recipient);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setTimeSlot(timeSlot);
        appointment.setTotalPrice(finalPrice);
        appointment.setNotes(notes);
        appointment.setStatus("PENDING");

        return appointmentRepository.save(appointment);
    }

    // เลื่อนวัน-เวลานัดหมาย
    public Appointment rescheduleAppointment(Long appointmentId, LocalDate newDate, String newTimeSlot) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบนัดหมาย"));

        appointment.setAppointmentDate(newDate);
        appointment.setTimeSlot(newTimeSlot);
        return appointmentRepository.save(appointment);
    }

    // ยกเลิกนัดหมาย
    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบนัดหมาย"));

        appointment.setStatus("CANCELLED");
        return appointmentRepository.save(appointment);
    }

    // แอดมินมอบหมายแพทย์และปรับสถานะเป็น CONFIRMED
    public Appointment assignDoctor(Long appointmentId, Long doctorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบนัดหมาย"));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบข้อมูลแพทย์"));

        appointment.setDoctor(doctor);
        appointment.setStatus("CONFIRMED");
        return appointmentRepository.save(appointment);
    }
}