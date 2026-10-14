package th.ac.mju.itsci.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // ✅ กลับมาเป็นแบบปกติ
public class ProjectVaccineApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectVaccineApplication.class, args);
    }
}