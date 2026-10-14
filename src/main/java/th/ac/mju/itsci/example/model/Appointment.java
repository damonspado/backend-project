package th.ac.mju.itsci.example.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String appointmentCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"password", "createdAt"})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vaccine_id", nullable = false)
    private Vaccine vaccine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id", nullable = false)
    @JsonIgnoreProperties("user")
    private Recipient recipient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private String timeSlot;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private String status = "PENDING";

    private String notes;

    // 🔥 เพิ่มคอลัมน์เก็บชื่อแพทย์ผู้ฉีดวัคซีน
    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Appointment() {}

    public Appointment(String appointmentCode, User user, Vaccine vaccine, Recipient recipient,
                       LocalDate appointmentDate, String timeSlot, Double totalPrice, String notes) {
        this.appointmentCode = appointmentCode;
        this.user = user;
        this.vaccine = vaccine;
        this.recipient = recipient;
        this.appointmentDate = appointmentDate;
        this.timeSlot = timeSlot;
        this.totalPrice = totalPrice;
        this.notes = notes;
        this.status = "PENDING";
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.appointmentCode == null || this.appointmentCode.isEmpty()) {
            this.appointmentCode = "APT-" + System.currentTimeMillis();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAppointmentCode() { return appointmentCode; }
    public void setAppointmentCode(String appointmentCode) { this.appointmentCode = appointmentCode; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Vaccine getVaccine() { return vaccine; }
    public void setVaccine(Vaccine vaccine) { this.vaccine = vaccine; }

    public Recipient getRecipient() { return recipient; }
    public void setRecipient(Recipient recipient) { this.recipient = recipient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // 🔥 เพิ่ม Getter/Setter สำหรับชื่อแพทย์
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}