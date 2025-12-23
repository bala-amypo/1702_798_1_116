package com.example.demo.service.impl;

// ... existing imports ...

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    // Remove PasswordEncoder dependency temporarily
    // private final PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserRepository userRepository) {
        // Remove PasswordEncoder from constructor
        this.userRepository = userRepository;
        // this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public User registerUser(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("Email already registered");
        }
        
        User user = new User();
        user.setEmail(email);
        // Use plain text temporarily (NOT for production)
        user.setPassword(password);  // Instead of passwordEncoder.encode(password)
        
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);
        
        return userRepository.save(user);
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}