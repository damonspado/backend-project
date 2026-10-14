//package th.ac.mju.itsci.example.model;
//
//import jakarta.persistence.*;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//@Entity
//@Table(name = "vaccines")
//public class Vaccine {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    private String description;
//    private double price;
//    private double discountPrice;
//
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    @JsonIgnoreProperties("vaccines") // 👈 เพิ่มบรรทัดนี้ครับ
//    private Category category;
//
//    public Vaccine() {}
//
//    // Getters and Setters...
//    public Long getId() { return id; }
//    
//    public void setId(Long id) { this.id = id; }
//    public String getName() { return name; }
//    
//    public void setName(String name) { this.name = name; }
//    public String getDescription() { return description; }
//    
//    public void setDescription(String description) { this.description = description; }
//    public double getPrice() { return price; }
//    
//    public void setPrice(double price) { this.price = price; }
//    
//    public double getDiscountPrice() { return discountPrice; }
//    
//    public void setDiscountPrice(double discountPrice) { this.discountPrice = discountPrice; }
//    
//    public Category getCategory() { return category; }
//    
//    public void setCategory(Category category) { this.category = category; }
//}






package th.ac.mju.itsci.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vaccines")
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(name = "discount_price")
    private double discountPrice;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // 🔥 เพิ่มความสัมพันธ์กับ Doctor (แพทย์ผู้รับผิดชอบประจำวัคซีน)
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public Vaccine() {}

    public Vaccine(String name, String description, double price, double discountPrice, String imageUrl, Category category, Doctor doctor) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.imageUrl = imageUrl;
        this.category = category;
        this.doctor = doctor;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // 🔥 Getter และ Setter ของ Doctor
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}