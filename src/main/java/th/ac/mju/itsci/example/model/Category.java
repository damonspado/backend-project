package th.ac.mju.itsci.example.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @JsonIgnore // 👈 ต้องมีตัวนี้ด้วยครับ
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Vaccine> vaccines = new ArrayList<>();

    public Category() {}

    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters ทั้งหมด...
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public List<Vaccine> getVaccines() { return vaccines; }
    public void setVaccines(List<Vaccine> vaccines) { this.vaccines = vaccines; }
}