package th.ac.mju.itsci.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import th.ac.mju.itsci.example.model.Recipient;
import th.ac.mju.itsci.example.service.RecipientService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // สำคัญมาก: อนุญาตให้ Next.js ยิงเข้ามาได้
@RestController
@RequestMapping("/api/recipients")
public class RecipientController {

    private final RecipientService recipientService;

    public RecipientController(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    // GET http://localhost:8080/api/recipients?userId=1
    @GetMapping
    public ResponseEntity<List<Recipient>> getRecipients(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(recipientService.getRecipientsByUserId(userId));
    }

    // POST http://localhost:8080/api/recipients?userId=1
    @PostMapping
    public ResponseEntity<Recipient> addRecipient(
            @RequestParam(name = "userId") Long userId,
            @RequestBody Recipient recipient) {
        Recipient saved = recipientService.addRecipient(userId, recipient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // DELETE http://localhost:8080/api/recipients/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipient(@PathVariable(name = "id") Long id) {
        recipientService.deleteRecipient(id);
        return ResponseEntity.noContent().build();
    }
}