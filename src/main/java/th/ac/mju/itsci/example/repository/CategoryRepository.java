package th.ac.mju.itsci.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import th.ac.mju.itsci.example.model.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
}