package me.deit.server.security;

import me.deit.server.hobby.Hobby;
import me.deit.server.utility.jwt.JwtUtils;
import me.deit.server.user.Gender;
import me.deit.server.user.Preference;
import me.deit.server.user.User;
import me.deit.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail()
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }


        Map<String, String> hobbies = signUpRequest.getHobbies();
        Set<Hobby> userHobbies = new HashSet<>();
        for (Map.Entry<String, String> hobby : hobbies.entrySet()) {
            userHobbies.add(new Hobby(Long.parseLong(hobby.getKey()), hobby.getValue()));
        }

        User user = new User(
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getPhoneNumber(),
                getGenderFromSignRequest(signUpRequest.getGender()),
                getPreferenceFromSignRequest(signUpRequest.getPreference()),
                signUpRequest.getDescription(),
                userHobbies
        );

        User s = userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest(user.getEmail(), signUpRequest.getPassword());

        return authenticateUser(loginRequest);
    }

    @GetMapping("/validatetoken")
    public Map<String, Boolean> validateJwtToken(@RequestParam String token) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", jwtUtils.validateJwtToken(token));
        return response;
    }

    private Preference getPreferenceFromSignRequest(String preference) {
        switch (preference.toUpperCase()) {
            case "MEN":
                return Preference.MEN;
            case "WOMEN":
                return Preference.WOMEN;
        }

        return null;
    }

    private Gender getGenderFromSignRequest(String gender) {
        switch (gender.toUpperCase()) {
            case "MALE":
                return Gender.MALE;
            case "FEMALE":
                return  Gender.FEMALE;
            case "OTHER":
                return  Gender.OTHER;
        }

        return null;
    }
}
