package ExpenSplit.demo.service;

import ExpenSplit.demo.DTOs.LoginRequest;
import ExpenSplit.demo.DTOs.RegisterRequest;
import ExpenSplit.demo.DTOs.UserResponse;
import ExpenSplit.demo.entity.User;
import ExpenSplit.demo.exception.ConflictException;
import ExpenSplit.demo.exception.UnauthorizedException;
import ExpenSplit.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ✅ Register
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already exists");
        }

        User user = new User();
        user.setName(request.name());
        user.setUsername(request.username());
        user.setEmail(request.email());

        // 🔐 Hash password
        user.setPassword(encoder.encode(request.password()));

        user.setCreatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);

        return toResponse(saved);
    }

    // ✅ Login
    public UserResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid email or password")
                );

        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        return toResponse(user);
    }

    private UserResponse toResponse(User u) {
        return new UserResponse(
                u.getId(),
                u.getName(),
                u.getUsername(),
                u.getEmail()
        );
    }
}

