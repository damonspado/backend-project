//package th.ac.mju.itsci.example.model;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "doctors")
//public class Doctor {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    // ความเชี่ยวชาญ เช่น "กุมารแพทย์", "อายุรแพทย์", "สัตวแพทย์ผู้เชี่ยวชาญ"
//    private String specialty;
//
//    // เลขที่ใบอนุญาตประกอบวิชาชีพเวชกรรม / การสัตวแพทย์
//    private String licenseNumber;
//
//    // ประเภทแพทย์: "HUMAN_DOCTOR" (แพทย์คน) หรือ "VET" (สัตวแพทย์)
//    @Column(nullable = false)
//    private String doctorType;
//
//    // เบอร์โทรศัพท์ติดต่อภายในคลินิก/โรงพยาบาล
//    private String phoneNumber;
//
//    public Doctor() {}
//
//    public Doctor(String name, String specialty, String licenseNumber, String doctorType, String phoneNumber) {
//        this.name = name;
//        this.specialty = specialty;
//        this.licenseNumber = licenseNumber;
//        this.doctorType = doctorType != null ? doctorType : "HUMAN_DOCTOR";
//        this.phoneNumber = phoneNumber;
//    }
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSpecialty() {
//        return specialty;
//    }
//
//    public void setSpecialty(String specialty) {
//        this.specialty = specialty;
//    }
//
//    public String getLicenseNumber() {
//        return licenseNumber;
//    }
//
//    public void setLicenseNumber(String licenseNumber) {
//        this.licenseNumber = licenseNumber;
//    }
//
//    public String getDoctorType() {
//        return doctorType;
//    }
//
//    public void setDoctorType(String doctorType) {
//        this.doctorType = doctorType;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//}







package th.ac.mju.itsci.example.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String specialty;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "doctor_type", nullable = false)
    private String doctorType;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;

    public Doctor() {}

    public Doctor(String name, String email, String password, String specialty, String licenseNumber, String doctorType, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.specialty = specialty;
        this.licenseNumber = licenseNumber;
        this.doctorType = doctorType != null ? doctorType : "HUMAN_DOCTOR";
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getDoctorType() { return doctorType; }
    public void setDoctorType(String doctorType) { this.doctorType = doctorType; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
}



