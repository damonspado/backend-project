package th.ac.mju.itsci.example.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  // ✅ เก็บไว้
import org.springframework.stereotype.Service;
import th.ac.mju.itsci.example.model.User;
import th.ac.mju.itsci.example.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    // ✅ เปลี่ยนจาก PasswordEncoder (interface) → BCryptPasswordEncoder (concrete class)
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("อีเมลนี้ถูกใช้งานแล้วในระบบ");
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("อีเมลหรือรหัสผ่านไม่ถูกต้อง");
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("อีเมลหรือรหัสผ่านไม่ถูกต้อง");
        }
        return user;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}