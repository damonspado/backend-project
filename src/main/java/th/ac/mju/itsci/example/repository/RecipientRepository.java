package th.ac.mju.itsci.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import th.ac.mju.itsci.example.model.Recipient;
import java.util.List;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    
    // ดึงรายชื่อผู้รับวัคซีนทั้งหมดที่เป็นของ User คนนี้
    List<Recipient> findByUserId(Long userId);
}