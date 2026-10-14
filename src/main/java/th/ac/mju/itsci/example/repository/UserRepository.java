package th.ac.mju.itsci.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import th.ac.mju.itsci.example.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // ค้นหาผู้ใช้จาก email สำหรับใช้ใน Login
    Optional<User> findByEmail(String email);
    
    // ตรวจสอบว่ามี email นี้ลงทะเบียนแล้วหรือไม่
    boolean existsByEmail(String email);
}