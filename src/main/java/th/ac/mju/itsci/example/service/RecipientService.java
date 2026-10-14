package th.ac.mju.itsci.example.service;

import org.springframework.stereotype.Service;
import th.ac.mju.itsci.example.model.Recipient;
import th.ac.mju.itsci.example.model.User;
import th.ac.mju.itsci.example.repository.RecipientRepository;
import th.ac.mju.itsci.example.repository.UserRepository;

import java.util.List;

@Service
public class RecipientService {

    private final RecipientRepository recipientRepository;
    private final UserRepository userRepository;

    public RecipientService(RecipientRepository recipientRepository, UserRepository userRepository) {
        this.recipientRepository = recipientRepository;
        this.userRepository = userRepository;
    }

    // ดึงรายชื่อผู้รับวัคซีนทั้งหมดของ User คนนั้น
    public List<Recipient> getRecipientsByUserId(Long userId) {
        return recipientRepository.findByUserId(userId);
    }

    // บันทึกผู้รับวัคซีนใหม่ ผูกกับ User
    public Recipient addRecipient(Long userId, Recipient recipient) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบผู้ใช้งาน ID: " + userId));
        recipient.setUser(user);
        return recipientRepository.save(recipient);
    }

    // ลบผู้รับวัคซีน
    public void deleteRecipient(Long recipientId) {
        recipientRepository.deleteById(recipientId);
    }
}