package th.ac.mju.itsci.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "recipients")
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // HUMAN หรือ PET

    private String species;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    // 👇 สำคัญมาก: ใส่ JsonIgnoreProperties เพื่อไม่ให้ดึงข้อมูล user วนลูป
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
    private User user;

    public Recipient() {}

    // Getters and Setters ...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}