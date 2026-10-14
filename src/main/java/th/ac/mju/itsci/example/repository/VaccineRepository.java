package th.ac.mju.itsci.example.repository;

import th.ac.mju.itsci.example.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    // ค้นหาวัคซีนด้วย categoryId ของ Category
    List<Vaccine> findByCategoryCategoryId(String categoryId);
}