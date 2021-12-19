package me.deit.server.user;

import me.deit.server.exceptions.UserExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@RequestParam String userId) {
        Optional<User> user = userRepository.findById(Long.parseLong(userId));

        if (user.isEmpty()) {
            return new ResponseEntity<>(UserExceptions.USER_NOT_FOUND_ID, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(user);
    }
}