//package th.ac.mju.itsci.example.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import th.ac.mju.itsci.example.model.User;
//import th.ac.mju.itsci.example.service.UserService;
//
//import java.util.Map;
//
//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final UserService userService;
//
//    public AuthController(UserService userService) {
//        this.userService = userService;
//    }
//
//    // Endpoint สมัครสมาชิก: POST http://localhost:8080/api/auth/register
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody User user) {
//        try {
//            User newUser = userService.register(user);
//            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
//        }
//    }
//
//    // Endpoint เข้าสู่ระบบ: POST http://localhost:8080/api/auth/login
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
//        try {
//            String email = credentials.get("email");
//            String password = credentials.get("password");
//            User user = userService.login(email, password);
//            return ResponseEntity.ok(user);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
//        }
//    }
//}




package th.ac.mju.itsci.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import th.ac.mju.itsci.example.model.User;
import th.ac.mju.itsci.example.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ✅ สร้าง BCryptPasswordEncoder ไว้ใช้ทั้งคลาส (สร้างครั้งเดียว)
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ==========================================
    // 1. สมัครสมาชิก (Register)
    // ==========================================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        // เช็คก่อนว่ามีอีเมลนี้ในระบบหรือยัง
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "อีเมลนี้มีในระบบแล้ว"));
        }

        // ✅ เข้ารหัสรหัสผ่านด้วย BCrypt
        String hashedPassword = encoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // บันทึกลง Database
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // ==========================================
    // 2. เข้าสู่ระบบ (Login)
    // ==========================================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // ค้นหา User จากอีเมล
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "ไม่พบอีเมลนี้ในระบบ"));
        }

        User user = userOpt.get();

        // ✅ ตรวจสอบรหัสผ่านด้วย BCrypt
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "รหัสผ่านไม่ถูกต้อง"));
        }

        return ResponseEntity.ok(user);
    }
}