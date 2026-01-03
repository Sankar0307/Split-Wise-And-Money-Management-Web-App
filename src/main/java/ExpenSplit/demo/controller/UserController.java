package ExpenSplit.demo.controller;

import ExpenSplit.demo.DTOs.LoginRequest;
import ExpenSplit.demo.DTOs.RegisterRequest;
import ExpenSplit.demo.DTOs.UserResponse;
import ExpenSplit.demo.entity.User;
import ExpenSplit.demo.repository.UserRepository;
import ExpenSplit.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}

